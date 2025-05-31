package com.AttendanceMonitoring.university_attendance_system_backend.controller;


import com.AttendanceMonitoring.university_attendance_system_backend.model.Course;
import com.AttendanceMonitoring.university_attendance_system_backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course)
    {
        int result = courseService.addCourse(course);

        if(result>0)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body("Course Added Successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add course");
        }
    }


    @GetMapping("/getCourses")
    public ResponseEntity<List<Course>> getCourses(){
        List<Course> courseList = courseService.getCourses();
        if(courseList.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            return ResponseEntity.ok(courseList);
        }
    }

    @GetMapping("/getCoursesByStudentId/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudentId(@PathVariable String studentId){
        List<Course> courseList = courseService.getCoursesByStudentId(studentId);
        if(courseList.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            return ResponseEntity.ok(courseList);
        }
    }
}
