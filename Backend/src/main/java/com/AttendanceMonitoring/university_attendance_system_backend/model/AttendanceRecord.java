package com.AttendanceMonitoring.university_attendance_system_backend.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class AttendanceRecord {
    private int record_id;
    private String registration_number;
    private String course_code;
    private LocalDate date;
    private String status;
    private String remarks;
    private Integer recorded_by;
    private Timestamp created_at;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public int getRecord_id() {
        return record_id;
    }
    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getRegistration_number() {
        return registration_number;
    }
    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getCourse_code() {
        return course_code;
    }
    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getRecorded_by() {
        return recorded_by;
    }
    public void setRecorded_by(Integer recorded_by) {
        this.recorded_by = recorded_by;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
