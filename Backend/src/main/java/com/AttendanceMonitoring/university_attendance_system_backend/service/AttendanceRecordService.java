package com.AttendanceMonitoring.university_attendance_system_backend.service;

import com.AttendanceMonitoring.university_attendance_system_backend.dto.AttendanceByCourse;
import com.AttendanceMonitoring.university_attendance_system_backend.dto.AttendanceByDate;
import com.AttendanceMonitoring.university_attendance_system_backend.dto.AttendanceSummary;
import com.AttendanceMonitoring.university_attendance_system_backend.model.AttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.sql.Date;

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
        String sql =  "SELECT record_id, a.registration_number, course_code, date, status, remarks, recorded_by, a.created_at, first_name, last_name FROM attendance_records a JOIN students b ON a.registration_number = b.registration_number WHERE a.registration_number = ? AND a.course_code = ?;";
        return jdbcTemplate.query(sql, new Object[]{registration_number, course_code}, (rs, rowNum) -> mapRow(rs));
    }

    public AttendanceSummary getAttendanceSummaryByStudentAndCourse(String registrationNumber, String courseCode) {
        List<AttendanceRecord> records = getAttendanceByStudentAndCourse(registrationNumber, courseCode);
        System.out.println("Records : "+records);
        String firstName = records.isEmpty() ? "" : records.getFirst().getFirstName();
        System.out.println(records.getFirst().getFirstName());
        String lastName = records.isEmpty() ? "" : records.getFirst().getLastName();

        long presentCount = records.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("Present"))
                .count();

        long absentCount = records.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("Absent"))
                .count();

        long lateCount = records.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("Late"))
                .count();

        System.out.println(records.getFirst().getLastName());
        long totalSessions = records.size();

        AttendanceSummary summary = new AttendanceSummary();
        summary.setRegistrationNumber(registrationNumber);
        summary.setFirst_name(firstName);
        summary.setLast_name(lastName);
        summary.setCourseCode(courseCode);
        summary.setPresentCount(presentCount);
        summary.setAbsentCount(absentCount);
        summary.setLateCount(lateCount);
        summary.setTotalSessions(totalSessions);

        return summary;
    }


    private AttendanceRecord mapRow(ResultSet rs) throws SQLException {
        AttendanceRecord record = new AttendanceRecord();
        record.setRecord_id(rs.getInt("record_id"));
        record.setRegistration_number(rs.getString("registration_number"));
        record.setFirstName(rs.getString("first_name"));
        record.setLastName(rs.getString("last_name"));
        record.setCourse_code(rs.getString("course_code"));
        record.setDate(rs.getDate("date").toLocalDate());
        record.setStatus(rs.getString("status"));
        record.setRemarks(rs.getString("remarks"));
        record.setRecorded_by(rs.getObject("recorded_by") != null ? rs.getInt("recorded_by") : null);
        record.setCreated_at(rs.getTimestamp("created_at"));
        return record;
    }

    private AttendanceByDate mapRowByDate(ResultSet rs) throws SQLException {
        AttendanceByDate record = new AttendanceByDate();
        record.setDate(rs.getDate("date").toLocalDate());  // assuming AttendanceByDate.date is LocalDate
        record.setCourseCode(rs.getString("course_code"));
        record.setCourseName(rs.getString("course_name"));
        record.setStatus(rs.getString("status"));
        return record;
    }

    private AttendanceByCourse mapRowByCourse(ResultSet rs) throws SQLException {
        AttendanceByCourse record = new AttendanceByCourse();
        record.setRegistrationNumber(rs.getString("registration_number"));  // assuming AttendanceByDate.date is LocalDate
        record.setFirstName(rs.getString("first_name"));
        record.setLastName(rs.getString("last_name"));
        record.setStatus(rs.getString("status"));
        return record;
    }

    public List<AttendanceByDate> getAttendanceByDate(String registrationNumber, LocalDate date) {
        String sql = "SELECT a.date, a.course_code, b.course_name, a.status " +
                "FROM attendance_records a " +
                "JOIN courses b ON a.course_code = b.course_code " +
                "WHERE a.registration_number = ? AND a.date = ?";

        Date sqlDate = Date.valueOf(date);  // Convert LocalDate to java.sql.Date

        return jdbcTemplate.query(sql, new Object[]{registrationNumber, sqlDate}, (rs, rowNum) -> mapRowByDate(rs));
    }


    public List<AttendanceByCourse> getAttendanceByCourse(String courseCode, LocalDate date) {
        String sql = "SELECT a.registration_number , s.first_name, s.last_name, a.status  FROM attendance_records a JOIN students s ON a.registration_number = s.registration_number WHERE a.course_code = ? AND a.date = ?;";

        Date sqlDate = Date.valueOf(date);  // Convert LocalDate to java.sql.Date

        return jdbcTemplate.query(sql, new Object[]{courseCode, sqlDate}, (rs, rowNum) -> mapRowByCourse(rs));
    }


    public List<AttendanceByDate> getAttendanceByDateSummary(String registrationNumber, LocalDate date) {

       List<AttendanceByDate> records = getAttendanceByDate(registrationNumber, date);

    return records;
    }
}
