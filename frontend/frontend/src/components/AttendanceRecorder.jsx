import React, { useState } from "react";
import axios from "axios";
import ConfirmModal from "./ConfirmModal";  // import your modal

const AttendanceRecorder = ({ students, courseCode, selectedDate }) => {
  const [attendance, setAttendance] = useState({});
  const [showConfirm, setShowConfirm] = useState(false);

  const submitAttendance = () => {
            const todayDate = new Date().toISOString().slice(0, 10);
            const recordedBy = 1;

            const records = Object.entries(attendance).map(([registrationNumber, status]) => ({
              registration_number: registrationNumber,
              course_code: courseCode,
              date: selectedDate ? selectedDate : todayDate,
              status: status,
              remarks: "Nothing",
              recorded_by: recordedBy,
            }));

            console.log("Submitting records individually:", records);

            records.forEach(record => {

              const token = localStorage.getItem('token');
              axios
                .post("http://localhost:8080/attendance/add", record,
                  {headers: {'Authorization': `Bearer ${token}`}}
                )
                .then(() => {
                  console.log(`Attendance recorded for ${record.registration_number}`);
                })
                .catch(error => {
                  console.error(`Error submitting attendance for ${record.registration_number}:`, error);
                });
            });
            setAttendance({});
  };

  const handleSubmit = () => {
    setShowConfirm(true);
  };

  const confirmSubmit = () => {
    setShowConfirm(false);
    submitAttendance();
  };

  const cancelSubmit = () => {
    setShowConfirm(false);
  };

  return students.length > 0 ? (
    <>
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
            className="px-6 py-2 bg-yellow-600 text-white rounded hover:bg-green-500 transition-all duration-300"
            onClick={handleSubmit}
          >
            Submit Attendance
          </button>
        </div>
      </div>

      {showConfirm && (
        <ConfirmModal
          message="Are you sure you want to submit the attendance?"
          onConfirm={confirmSubmit}
          onCancel={cancelSubmit}
        />
      )}
    </>
  ) : (
    <p>Loading students...</p>
  );
};

export default AttendanceRecorder;
