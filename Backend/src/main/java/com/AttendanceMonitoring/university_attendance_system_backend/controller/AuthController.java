package com.AttendanceMonitoring.university_attendance_system_backend.controller;

import com.AttendanceMonitoring.university_attendance_system_backend.dto.LoginRequest;
import com.AttendanceMonitoring.university_attendance_system_backend.dto.UserResponse;
import com.AttendanceMonitoring.university_attendance_system_backend.model.Role;
import com.AttendanceMonitoring.university_attendance_system_backend.model.User;
import com.AttendanceMonitoring.university_attendance_system_backend.security.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class AuthController {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            User user = jdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE email = ?",
                    (rs, rowNum) -> {
                        User u = new User();
                        u.setId(rs.getLong("id"));
                        u.setEmail(rs.getString("email"));
                        u.setPassword(rs.getString("password"));
                        u.setRole(Role.valueOf(rs.getString("role").toUpperCase())); // 👈 this is essential

                        return u;
                    },
                    username
            );

            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
                UserResponse userResponse = new UserResponse(user.getEmail(), user.getRole().name());

                return ResponseEntity.ok(Map.of(
                        "user", userResponse,
                        "token", token
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid email or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }
    }
}
