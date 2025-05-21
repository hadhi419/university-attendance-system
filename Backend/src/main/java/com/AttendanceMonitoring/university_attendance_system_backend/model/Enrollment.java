package com.AttendanceMonitoring.university_attendance_system_backend.model;

import java.sql.Timestamp;

public class Enrollment {

    private int enrollment_id;
    private String registration_number;
    private String course_code;
    private Timestamp enrollment_date;

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollment_id=" + enrollment_id +
                ", registration_number='" + registration_number + '\'' +
                ", course_code='" + course_code + '\'' +
                ", enrollment_date=" + enrollment_date +
                '}';
    }

    public int getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(int enrollment_id) {
        this.enrollment_id = enrollment_id;
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

    public Timestamp getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(Timestamp enrollment_date) {
        this.enrollment_date = enrollment_date;
    }
}
