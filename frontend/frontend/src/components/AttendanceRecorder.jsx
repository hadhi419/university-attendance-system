import React, { useState, useEffect } from "react";
import axios from "axios";

const AttendanceRecorder = ( {students, courseCode} ) => {

  const [attendance, setAttendance] = useState({});
  

  const handleSubmit = () => {
          const todayDate = new Date().toISOString().slice(0, 10);
          const recordedBy = 1;

            const records = Object.entries(attendance).map(([registrationNumber, status]) => ({
              registration_number: registrationNumber,
              course_code: courseCode,
              date: todayDate,
              status: status,
              remarks: "Nothing",
              recorded_by: recordedBy
            }));

          console.log("Submitting records individually:", records);

          records.forEach(record => {
            axios.post('http://localhost:8080/attendance/add', record)
              .then(response => {
                console.log(`Attendance recorded for ${record.registration_number}`);
              })
              .catch(error => {
                console.error(`Error submitting attendance for ${record.registration_number}:`, error);
              });
          });
          setAttendance({});
  };

  return (
    students.length > 0 ? (
  <div className="attendance-recorder w-full px-8 py-6">
    <h2 className="text-2xl font-semibold mb-6">Attendance Recorder</h2>

    {/* HEADER */}
    <div className="flex border-b border-gray-300 pb-2 mb-2 font-semibold text-gray-700">
      <div className="flex-1">Registration Number</div>
      <div className="flex-1">Name</div>
      <div className="flex-1">Status</div>
    </div>

    <div className="flex flex-col space-y-2">
      {students.map((student, index) => (
        <div
          key={index}
          className="flex items-center p-4 border border-gray-300 rounded-md shadow-sm"
        >
          <div className="flex-1">{student.registrationNumber}</div>
          <div className="flex-1">{student.firstName + " " + student.lastName}</div>
          <div className="flex-1">
            <select
              value={attendance[student.registrationNumber] || ""}
              onChange={(e) =>
                setAttendance((prev) => ({
                  ...prev,
                  [student.registrationNumber]: e.target.value,
                }))
              }
              className="border border-gray-300 rounded px-3 py-1 w-full"
            >
              <option value="" disabled>
                Select Status
              </option>
              <option value="Present">Present</option>
              <option value="Absent">Absent</option>
              <option value="Late">Late</option>
            </select>
          </div>
        </div>
      ))}
    </div>

    <div className="flex justify-end mt-6">
      <button
        className="px-6 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
        onClick={handleSubmit}
      >
        Submit Attendance
      </button>
    </div>
  </div>
) : (
  <p>Loading students...</p>
));
}

export default AttendanceRecorder;
