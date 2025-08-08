package com.AttendanceMonitoring.university_attendance_system_backend.controller;

import com.AttendanceMonitoring.university_attendance_system_backend.dto.LoginRequest;
import com.AttendanceMonitoring.university_attendance_system_backend.dto.UserResponse;
import com.AttendanceMonitoring.university_attendance_system_backend.model.Role;
import com.AttendanceMonitoring.university_attendance_system_backend.model.User;
import com.AttendanceMonitoring.university_attendance_system_backend.security.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://university-attendance-system-git-feat-5018fb-hadhi419s-projects.vercel.app"})
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


//        boolean captchaVerified = verifyCaptcha(loginRequest.getCaptcha());
//
//        if (!captchaVerified) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Captcha verification failed");
//        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        System.out.println(username);
        System.out.println(password);

        try {

            System.out.println("User found");

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

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    private boolean verifyCaptcha(String captchaResponse) {


        String url = "https://www.google.com/recaptcha/api/siteverify";
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        System.out.println("Recaptcha secret"+recaptchaSecret);
        params.add("secret", recaptchaSecret);
        params.add("response", captchaResponse);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, params, Map.class);
        Map<String, Object> body = response.getBody();

        System.out.println(body);

        return (Boolean) body.get("success");
    }



}
