package com.AttendanceMonitoring.university_attendance_system_backend.controller;

import com.AttendanceMonitoring.university_attendance_system_backend.model.Enrollment;
import com.AttendanceMonitoring.university_attendance_system_backend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.AttendanceMonitoring.university_attendance_system_backend.dto.EnrollmentDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://university-attendance-system-git-feat-5018fb-hadhi419s-projects.vercel.app"})
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<Map<String, Object>> enrollStudent(@RequestBody List<EnrollmentDTO> enrollments) {
        Map<String, Integer> result = enrollmentService.bulkEnroll(enrollments);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Enrollment processed.");
        response.put("successful", result.get("success"));
        response.put("skipped_duplicates", result.get("skipped"));

        return ResponseEntity.ok(response);
    }



    @GetMapping("/all")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }
}
