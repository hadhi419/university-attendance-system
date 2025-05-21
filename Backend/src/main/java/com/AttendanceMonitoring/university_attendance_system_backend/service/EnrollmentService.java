package com.AttendanceMonitoring.university_attendance_system_backend.service;

import com.AttendanceMonitoring.university_attendance_system_backend.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int enrollStudent(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (registration_number, course_code, enrollment_date) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                enrollment.getRegistration_number(),
                enrollment.getCourse_code(),
                enrollment.getEnrollment_date() != null ? enrollment.getEnrollment_date() : new java.sql.Timestamp(System.currentTimeMillis())
        );
    }


    public List<Enrollment> getAllEnrollments() {
        String sql = "SELECT * FROM enrollments";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    private Enrollment mapRow(ResultSet rs) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollment_id(rs.getInt("enrollment_id"));
        enrollment.setRegistration_number(rs.getString("registration_number"));
        enrollment.setCourse_code(rs.getString("course_code"));
        enrollment.setEnrollment_date(rs.getTimestamp("enrollment_date"));
        return enrollment;
    }
}
