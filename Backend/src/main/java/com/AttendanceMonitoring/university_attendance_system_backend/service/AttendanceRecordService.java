package com.AttendanceMonitoring.university_attendance_system_backend.service;

import com.AttendanceMonitoring.university_attendance_system_backend.model.AttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AttendanceRecordService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addAttendanceRecord(AttendanceRecord record) {
        String sql = "INSERT INTO attendance_records " +
                    "(registration_number, course_code, date, status, remarks, recorded_by) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                record.getRegistration_number(),
                record.getCourse_code(),
                record.getDate(),
                record.getStatus(),
                record.getRemarks(),
                record.getRecorded_by());
    }

    public List<AttendanceRecord> getAllAttendanceRecords() {
        String sql = "SELECT * FROM attendance_records";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    public List<AttendanceRecord> getAttendanceByStudentAndCourse(String registration_number, String course_code) {
        String sql = "SELECT * FROM attendance_records WHERE registration_number = ? AND course_code = ?";
        return jdbcTemplate.query(sql, new Object[]{registration_number, course_code}, (rs, rowNum) -> mapRow(rs));
    }

    private AttendanceRecord mapRow(ResultSet rs) throws SQLException {
        AttendanceRecord record = new AttendanceRecord();
        record.setRecord_id(rs.getInt("record_id"));
        record.setRegistration_number(rs.getString("registration_number"));
        record.setCourse_code(rs.getString("course_code"));
        record.setDate(rs.getDate("date").toLocalDate());
        record.setStatus(rs.getString("status"));
        record.setRemarks(rs.getString("remarks"));
        record.setRecorded_by(rs.getObject("recorded_by") != null ? rs.getInt("recorded_by") : null);
        record.setCreated_at(rs.getTimestamp("created_at"));
        return record;
    }
}
