package com.AttendanceMonitoring.university_attendance_system_backend.dto;


public class AttendanceSummary {
    private String registrationNumber;
    private String courseCode;
    private String first_name;
    private String last_name;
    private long presentCount;
    private long absentCount;
    private long lateCount;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    private long totalSessions;


    public AttendanceSummary(String registrationNumber, String courseCode, String first_name, String last_name, long presentCount, long absentCount, long lateCount, long totalSessions) {
        this.registrationNumber = registrationNumber;
        this.courseCode = courseCode;
        this.first_name = first_name;
        this.last_name = last_name;
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.lateCount = lateCount;
        this.totalSessions = totalSessions;
    }

    public AttendanceSummary() {
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public long getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(long presentCount) {
        this.presentCount = presentCount;
    }

    public long getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(long absentCount) {
        this.absentCount = absentCount;
    }

    public long getLateCount() {
        return lateCount;
    }

    public void setLateCount(long lateCount) {
        this.lateCount = lateCount;
    }

    public long getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(long totalSessions) {
        this.totalSessions = totalSessions;
    }
}


