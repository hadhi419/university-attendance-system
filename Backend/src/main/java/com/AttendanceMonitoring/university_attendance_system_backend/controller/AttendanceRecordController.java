package com.AttendanceMonitoring.university_attendance_system_backend.controller;

import com.AttendanceMonitoring.university_attendance_system_backend.dto.AttendanceByCourse;
import com.AttendanceMonitoring.university_attendance_system_backend.dto.AttendanceByDate;
import com.AttendanceMonitoring.university_attendance_system_backend.dto.AttendanceSummary;
import com.AttendanceMonitoring.university_attendance_system_backend.model.AttendanceRecord;
import com.AttendanceMonitoring.university_attendance_system_backend.service.AttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://university-attendance-system-git-feat-5018fb-hadhi419s-projects.vercel.app"})
@RequestMapping("/attendance")
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @PostMapping("/add")
    public ResponseEntity<String> addAttendance(@RequestBody AttendanceRecord record) {
        try {
            int result = attendanceRecordService.addAttendanceRecord(record);
            System.out.println("Result: " + result);
            if (result > 0) {
                return ResponseEntity.status(201).body("Attendance record added successfully.");
            } else {
                return ResponseEntity.status(500).body("Failed to add attendance record.");
            }
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(409).body("Attendance already exists for this student, course, and date.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error.");
        }
    }

    @GetMapping("/all")
    public List<AttendanceRecord> getAllAttendanceRecords() {
        return attendanceRecordService.getAllAttendanceRecords();
    }

    @GetMapping("/student/{registrationNumber}/by-date")
    public List<AttendanceByDate> getAttendanceByDate(
            @PathVariable String registrationNumber,
            @RequestParam LocalDate date
    ) {
        return attendanceRecordService.getAttendanceByDate(registrationNumber, date);
    }

    @GetMapping("/student/{registration_number}/course/{course_code}")
    public AttendanceSummary getAttendanceByStudentAndCourse(
            @PathVariable String registration_number,
            @PathVariable String course_code) {
        return attendanceRecordService.getAttendanceSummaryByStudentAndCourse(registration_number, course_code);
    }

    @GetMapping("/course/{courseCode}/by-date")
    public List<AttendanceByCourse> getAttendanceByCourse(
            @PathVariable String courseCode,
            @RequestParam LocalDate date
    ) {
        return attendanceRecordService.getAttendanceByCourse(courseCode , date);
    }




}
