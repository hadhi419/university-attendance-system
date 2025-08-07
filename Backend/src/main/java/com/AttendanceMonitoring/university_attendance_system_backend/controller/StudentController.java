package com.AttendanceMonitoring.university_attendance_system_backend.controller;

import com.AttendanceMonitoring.university_attendance_system_backend.dto.EnrollmentDTO;
import com.AttendanceMonitoring.university_attendance_system_backend.model.Enrollment;
import com.AttendanceMonitoring.university_attendance_system_backend.model.Student;
import com.AttendanceMonitoring.university_attendance_system_backend.service.StudentService;
import com.AttendanceMonitoring.university_attendance_system_backend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


import java.sql.Timestamp;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = {"http://localhost:5173", "https://university-attendance-system-git-feat-5018fb-hadhi419s-projects.vercel.app"})
public class StudentController {

    @Autowired
    private StudentService studentService;
    private EnrollmentService enrollmentService;

    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        int result = studentService.addStudent(student);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add student.");
        }
    }

    @GetMapping("/getStudents")
    public  ResponseEntity<List<Student>> getStudents(){
        List<Student> studentList =  studentService.getStudents();
        if(studentList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            return ResponseEntity.ok(studentList);
        }

    }


    @GetMapping("/course/{course_code}")
    public  ResponseEntity<List<Student>> getStudents(@PathVariable String course_code){
        List<Student> studentList =  studentService.getStudentsByCourse(course_code);
        if(studentList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            return ResponseEntity.ok(studentList);
        }

    }

    @PostMapping("/bulk-enroll")
    public ResponseEntity<?> bulkEnroll(@RequestBody List<EnrollmentDTO> enrollments) {
        enrollmentService.bulkEnroll(enrollments);
        return ResponseEntity.ok(Map.of("message", "Students enrolled successfully!"));
    }


}
