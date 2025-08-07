package com.AttendanceMonitoring.university_attendance_system_backend.controller;

import com.AttendanceMonitoring.university_attendance_system_backend.model.User;
import com.AttendanceMonitoring.university_attendance_system_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://university-attendance-system-git-feat-5018fb-hadhi419s-projects.vercel.app"})
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService registrationService) {
        this.adminService = registrationService;
    }


    public static class RegistrationRequest {
        public String email;
        public String password;
        public String role;
    }



    @PostMapping("/addUser")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        try {
            User newUser = adminService.registerUser(request.email, request.password, request.role);
            // Don't return the password in response
            newUser.setPassword(null);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
