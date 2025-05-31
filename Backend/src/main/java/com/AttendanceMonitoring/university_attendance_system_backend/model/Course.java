package com.AttendanceMonitoring.university_attendance_system_backend.model;

import java.sql.Timestamp;

public class Course {

    private String course_code;
    private String course_name;
    private Timestamp created_at;

    // Getters and setters
    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_code='" + course_code + '\'' +
                ", course_name='" + course_name + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
