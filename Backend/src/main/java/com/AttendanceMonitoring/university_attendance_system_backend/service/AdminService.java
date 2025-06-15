package com.AttendanceMonitoring.university_attendance_system_backend.service;

import com.AttendanceMonitoring.university_attendance_system_backend.model.Role;
import com.AttendanceMonitoring.university_attendance_system_backend.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;  // <-- Add mail sender

    // Add JavaMailSender to constructor injection
    public AdminService(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Transactional
    public User registerUser(String email, String password, String roleStr) throws Exception {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?", Integer.class, email);

        if (count != null && count > 0) {
            throw new Exception("Email already taken");
        }

        String hashedPassword = passwordEncoder.encode(password);

        Role role;
        try {
            role = roleStr != null ? Role.valueOf(roleStr.toUpperCase()) : Role.STUDENT;
        } catch (IllegalArgumentException e) {
            role = Role.STUDENT;
        }

        jdbcTemplate.update(
                "INSERT INTO users (email, password, role) VALUES (?, ?, ?)",
                email, hashedPassword, role.name()
        );

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole(role);

        // Send email after saving user
        //sendRegistrationEmail(email, password);

        try {
            sendRegistrationEmail(email, password);
        } catch (Exception e) {
            // Log the email sending failure, but don't fail the registration
            System.err.println("Failed to send registration email: " + e.getMessage());
        }

        return user;
    }

    private void sendRegistrationEmail(String toEmail, String plainPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your New Account Details");
        message.setText(
                "Dear User,\n\n" +
                        "Your account has been successfully created\n" +
                        "Here are your login credentials:\n\n" +
                        "Email: " + toEmail + "\n" +
                        "Password: " + plainPassword + "\n\n" +
                        "Please change your password after logging in.\n\n" +
                        "Best regards,\n" +
                        "University Attendance Monitoring Team\n"
        );

        mailSender.send(message);
    }
}
