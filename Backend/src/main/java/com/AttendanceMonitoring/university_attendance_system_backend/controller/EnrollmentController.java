package com.AttendanceMonitoring.university_attendance_system_backend.controller;

import com.AttendanceMonitoring.university_attendance_system_backend.model.Enrollment;
import com.AttendanceMonitoring.university_attendance_system_backend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
origins = "https://university-attendance-system-git-feat-5018fb-hadhi419s-projects.vercel.app"
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudent(@RequestBody Enrollment enrollment) {
        int result = enrollmentService.enrollStudent(enrollment);
        return result > 0
                ? ResponseEntity.ok("Student enrolled successfully.")
                : ResponseEntity.badRequest().body("Enrollment failed.");
    }

    @GetMapping("/all")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }
}
