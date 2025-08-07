package com.AttendanceMonitoring.university_attendance_system_backend.service;

import com.AttendanceMonitoring.university_attendance_system_backend.dto.EnrollmentDTO;
import com.AttendanceMonitoring.university_attendance_system_backend.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import org.springframework.dao.DuplicateKeyException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    public Map<String, Integer> bulkEnroll(List<EnrollmentDTO> enrollmentDTOs) {
        String sql = "INSERT INTO enrollments (registration_number, course_code, enrollment_date) VALUES (?, ?, ?)";
        int successCount = 0;
        int duplicateCount = 0;

        for (EnrollmentDTO dto : enrollmentDTOs) {
            try {
                jdbcTemplate.update(sql,
                        dto.getRegistration_number(),
                        dto.getCourse_code(),
                        new Timestamp(System.currentTimeMillis()));
                successCount++;
            } catch (DuplicateKeyException e) {
                duplicateCount++; // ignore and count
            }
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("success", successCount);
        result.put("skipped", duplicateCount);
        return result;
    }



}
