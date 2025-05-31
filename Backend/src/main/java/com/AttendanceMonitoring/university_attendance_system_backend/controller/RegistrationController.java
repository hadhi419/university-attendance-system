package com.AttendanceMonitoring.university_attendance_system_backend.config;

import com.AttendanceMonitoring.university_attendance_system_backend.model.User;
import com.AttendanceMonitoring.university_attendance_system_backend.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // DTO for registration request (you can create a separate class if you want)
    public static class RegistrationRequest {
        public String username;
        public String password;
        public String role;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        try {
            User newUser = registrationService.registerUser(request.username, request.password, request.role);
            // Don't return the password in response
            newUser.setPassword(null);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
