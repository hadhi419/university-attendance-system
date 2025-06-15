package com.AttendanceMonitoring.university_attendance_system_backend.model;

public class User {

    private Long id;
    private String email;
    private String password;
    private Role role;  // changed from String to Role

    // No-args constructor
    public User() {
    }

    // All-args constructor
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
    }
}
