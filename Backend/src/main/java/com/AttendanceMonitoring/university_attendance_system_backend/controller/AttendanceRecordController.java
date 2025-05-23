package com.AttendanceMonitoring.university_attendance_system_backend.controller;

import com.AttendanceMonitoring.university_attendance_system_backend.model.AttendanceRecord;
import com.AttendanceMonitoring.university_attendance_system_backend.service.AttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @PostMapping("/add")
    public ResponseEntity<String> addAttendance(@RequestBody AttendanceRecord record) {
        int result = attendanceRecordService.addAttendanceRecord(record);
        if(result > 0) {
            return ResponseEntity.status(201).body("Attendance record added successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to add attendance record.");
        }
    }

    @GetMapping("/all")
    public List<AttendanceRecord> getAllAttendanceRecords() {
        return attendanceRecordService.getAllAttendanceRecords();
    }

    @GetMapping("/student/{registration_number}/course/{course_code}")
    public List<AttendanceRecord> getAttendanceByStudentAndCourse(
            @PathVariable String registration_number,
            @PathVariable String course_code) {
        return attendanceRecordService.getAttendanceByStudentAndCourse(registration_number, course_code);
    }
}
