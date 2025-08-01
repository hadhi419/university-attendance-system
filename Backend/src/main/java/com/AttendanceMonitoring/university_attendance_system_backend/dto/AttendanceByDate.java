package com.AttendanceMonitoring.university_attendance_system_backend.dto;

import java.time.LocalDate;

public class AttendanceByDate {

    private LocalDate date;
    private String courseCode;
    private String courseName;
    private String status;

    // Default constructor (required for frameworks like Jackson)
    public AttendanceByDate() {
    }

    // Parameterized constructor
    public AttendanceByDate(LocalDate date, String courseCode, String courseName, String status) {
        this.date = date;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.status = status;
    }

    // Getters and setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
