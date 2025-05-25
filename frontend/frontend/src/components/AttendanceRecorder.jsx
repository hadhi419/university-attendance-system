import React, { useState, useEffect } from "react";
import axios from "axios";

const AttendanceRecorder = () => {
  const [students, setStudents] = useState([]);
  const [attendance, setAttendance] = useState({});

  const fetchStudents = async () => {
    try {
      const response = await axios.get('http://localhost:8080/students/getStudents');
      setStudents(response.data);
    } catch (error) {
      console.error("Error fetching students:", error);
    }
  };

  useEffect(() => {
    fetchStudents();
  }, []);

  const handleSubmit = () => {
    const courseCode = "IT2214";
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
  };

  return (
    students.length > 0 ? (
     <div className="attendance-recorder w-full px-8 py-6">
        <h2 className="text-2xl font-semibold mb-6">Attendance Recorder</h2>

        <table className="w-full table-auto border-collapse">
            <thead>
            <tr className="bg-gray-100">
                <th className="text-left px-6 py-3 text-sm font-semibold text-gray-700">Student ID</th>
                <th className="text-left px-6 py-3 text-sm font-semibold text-gray-700">Name</th>
                <th className="text-left px-6 py-3 text-sm font-semibold text-gray-700">Attendance</th>
            </tr>
            </thead>
            <tbody>
            {students.map((student, index) => (
                <tr key={index} className="border-b">
                <td className="px-6 py-3">{student.registrationNumber}</td>
                <td className="px-6 py-3">{student.firstName + " " + student.lastName}</td>
                <td className="px-6 py-3">
                    <select className="border border-gray-300 rounded px-3 py-1 w-full">
                    <option>Select</option>
                    <option>Present</option>
                    <option>Absent</option>
                    </select>
                </td>
                </tr>
            ))}
            </tbody>
        </table>

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
    )
  );
};

export default AttendanceRecorder;
