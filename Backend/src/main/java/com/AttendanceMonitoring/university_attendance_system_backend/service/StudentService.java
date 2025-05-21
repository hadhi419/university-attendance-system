package com.AttendanceMonitoring.university_attendance_system_backend.service;

import com.AttendanceMonitoring.university_attendance_system_backend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;


@Service
public class StudentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addStudent(Student student) {
        String sql = "INSERT INTO students (registration_number, first_name, last_name, email) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                student.getRegistrationNumber(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail());
    }



    public List<Student> getStudents() {
        String sql = "SELECT * FROM students";

        return jdbcTemplate.query(sql, new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student = new Student();
                student.setRegistrationNumber(rs.getString("registration_number"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setCreatedAt(rs.getTimestamp("created_at"));
                return student;
            }
        });
    }

}
