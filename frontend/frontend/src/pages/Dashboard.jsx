import React, { useState } from 'react';
import axios from 'axios';
import AttendanceSummaryChart from '../components/AttendanceSummaryChart';

const Dashboard = () => {
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false); // Set default to false
  const [error, setError] = useState(null);
  const [courses, setCourses] = useState([]); // Default to empty array
  const [registrationNumber, setRegistrationNumber] = useState('');

  const fetchCourses = async () => {
    if (!registrationNumber.trim()) {
      setError(new Error("Registration Number cannot be empty!"));
      return;
    }

    setLoading(true);
    setError(null);
    setCourses([]); // clear previous data
    setResult(null); // clear previous chart data

    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://localhost:8080/courses/getCoursesByStudentId/${registrationNumber}`,
        {
              headers: {
                'Authorization': `Bearer ${token}`,
              },
            }
      );
      setCourses(response.data);
      setResult(response.data); // if AttendanceSummaryChart uses same data
      console.log("Fetched courses:", response.data);
    } catch (err) {
      console.error("Error fetching data:", err);
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  const fetchData = async (registrationNumber,courseCode) => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get(`http://localhost:8080/attendance/student/${registrationNumber}/course/${courseCode}`,
           {
              headers: {
                'Authorization': `Bearer ${token}`,
              },
            }
        );
        setResult(response.data);
      } catch (err) {
        console.error("Error fetching data:", err);
        setError(err);
      } finally {
        setLoading(false);
      } 
    }

  return (
    <>
      <div className="mx-6 flex justify-between border-gray-300 rounded overflow-hidden w-150">
        <input
          type="text"
          placeholder="Registration Number"
          className="flex-1 border border-gray-300 rounded px-3 py-2 outline-none mb-2 md:mb-0 mx-4"
          value={registrationNumber}
          onChange={(e) => setRegistrationNumber(e.target.value)}
        />
        <button
          className="bg-cyan-500 text-white px-4 py-2 rounded hover:bg-cyan-600 hover:rounded-xl transition-all duration-300 md:mt-0"
          onClick={fetchCourses}
          disabled={loading}
        >
          {loading ? 'Loading...' : 'Show Summary'}
        </button>
      </div>

      {error && (
        <p className="text-red-500 font-medium mt-2 mx-6">
          Error: {error.message}
        </p>
      )}

      {/* Display courses */}
      <div className="mx-6 mt-4 flex flex-col">
        {courses.length > 0 && (
          <>
          <h2 className="text-lg font-semibold mb-2 mx-10 mt-7">Select course to get the summary:</h2>
            <div className='grid w-full h-30 '>
              {courses.map((course, index) => (
                <div key={index} className="border-b border-gray-200 py-2">
                  <button className='bg-cyan-300 w-150 text-left text-gray-800 border border-gray-300 rounded-lg px-4 py-2 mx-10 shadow hover:shadow-md transition-all duration-300 hover:bg-cyan-500 hover:text-white font-medium' onClick={()=>fetchData(registrationNumber,course.course_code)}>{course.course_code} - {course.course_name}</button>
                </div>
              ))}

            </div>
            </>
      )}
      {/* Attendance Summary Chart */}
      {result && (
        <div className="flex items-center justify-center p-4 bg-gray-100 shadow-md mx-6 rounded-lg">
          <div className="p-4 w-full">
            <div className="shadow-md rounded-lg p-2">
              <AttendanceSummaryChart data={result} />
            </div>
          </div>
        </div>
      )}
      </div>
    </>
  );
};

export default Dashboard;
