package com.AttendanceMonitoring.university_attendance_system_backend.service;

import com.AttendanceMonitoring.university_attendance_system_backend.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CourseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int addCourse(Course course)
    {
        String sql = "INSERT INTO courses (course_code,course_name) VALUES(?,?)";
        return jdbcTemplate.update(sql,
                course.getCourse_code(),
                course.getCourse_name()
                );
    }


    public List<Course> getCourses()
    {
        String sql = "SELECT * FROM courses";

        return jdbcTemplate.query(sql, new RowMapper<Course>() {
            @Override
            public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Course course = new Course();
                course.setCourse_code(rs.getString("course_code"));
                course.setCourse_name(rs.getString("course_name"));

                return course;
            }
        });
    }

}
