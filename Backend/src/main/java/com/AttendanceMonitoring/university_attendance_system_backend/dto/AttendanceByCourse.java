package com.AttendanceMonitoring.university_attendance_system_backend.dto;

import java.time.LocalDate;

public class AttendanceByCourse {
    private String registrationNumber;
    private String firstName;
    private String lastName;
    private String status;

    public AttendanceByCourse(String registrationNumber, String firstName, String lastName, String status) {
        this.registrationNumber = registrationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public AttendanceByCourse() {
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AttendanceByCourse{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
