import React from 'react';
import AttendanceRecorder from '../components/AttendanceRecorder';
import { useState } from 'react';




import axios from 'axios';


const Record = () => {

  const [courseCode, setCourseCode] = useState("");
  const [students, setStudents] = useState([]);

  const fetchStudents = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/students/course/${courseCode}`);
      console.log("Fetched students:", response.data);
      setStudents(response.data);
    } catch (error) {
      console.error("Error fetching students:", error);
    }
  };

  
  return (
    <>
    <div className="mx-6 flex border border-gray-300 rounded overflow-hidden w-130">
      <input
        type="text"
        placeholder="Course Code"
        className="flex-1 px-3 py-2 outline-none"
        value={courseCode}
        onChange={(e) => setCourseCode(e.target.value)}
      />
      <button
        className="bg-cyan-500 text-white px-4 hover:bg-cyan-600 transition" onClick={fetchStudents}
      >
        Fetch Students
      </button>
    </div>
      <div>
      <AttendanceRecorder students={students} courseCode={courseCode} />
    </div>
    </>
  );
};

export default Record;
