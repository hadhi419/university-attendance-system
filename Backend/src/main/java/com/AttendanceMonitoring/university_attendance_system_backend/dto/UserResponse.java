package com.AttendanceMonitoring.university_attendance_system_backend.dto;

// UserResponse.java - DTO for sending user info back
public class UserResponse {
    private String email;
    private String role;

    public UserResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
