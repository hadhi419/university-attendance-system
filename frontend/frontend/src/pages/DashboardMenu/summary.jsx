import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AttendanceSummaryChart from '../../components/AttendanceSummaryChart';

const Dashboard = () => {
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [courses, setCourses] = useState([]);
  const [registrationNumber, setRegistrationNumber] = useState('');
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [showChart, setShowChart] = useState(false);

  const fetchCourses = async () => {
    if (!registrationNumber.trim()) {
      setError(new Error("Registration Number cannot be empty!"));
      return;
    }

    setLoading(true);
    setError(null);
    setCourses([]);
    setResult(null);
    setSelectedCourse(null);
    setShowChart(false);

    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(
        `http://localhost:8080/courses/getCoursesByStudentId/${registrationNumber}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setCourses(response.data);
      setResult(null);
    } catch (err) {
      console.error("Error fetching courses:", err);
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  const fetchData = async (registrationNumber, courseCode) => {
    setSelectedCourse(courseCode);
    setLoading(true);
    setShowChart(false); // Hide chart before loading new data
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(
        `http://localhost:8080/attendance/student/${registrationNumber}/course/${courseCode}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setResult(response.data);
    } catch (err) {
      console.error("Error fetching attendance:", err);
      setError(err);
      setResult(null);
    } finally {
      setLoading(false);
    }
  };

  // Trigger fade-in animation when result changes
  useEffect(() => {
    if (result) {
      // Small delay to trigger CSS transition
      const timer = setTimeout(() => setShowChart(true), 50);
      return () => clearTimeout(timer);
    } else {
      setShowChart(false);
    }
  }, [result]);

  const getChartHeight = () => {
    if (courses.length <= 2) return 'h-64';
    if (courses.length <= 4) return 'h-80';
    return 'h-[30rem]';
  };

  return (
    <div className="p-6">
      {/* Registration Input Section */}
      <div className="flex flex-col md:flex-row gap-4 mb-4">
        <input
          type="text"
          placeholder="Registration Number"
          className="flex-1 border border-gray-300 rounded px-3 py-2 outline-none"
          value={registrationNumber}
          onChange={(e) => setRegistrationNumber(e.target.value)}
        />
        <button
          className="bg-cyan-500 text-white px-4 py-2 rounded hover:bg-cyan-600 transition-all duration-300"
          onClick={fetchCourses}
          disabled={loading}
        >
          {loading ? 'Loading...' : 'Show Summary'}
        </button>
      </div>

      {/* Error Message */}
      {error && (
        <p className="text-red-500 font-medium mb-4">
          Error: {(error.status === 403 ? "No Summary Found." : error.message)}
        </p>
      )}

      {/* Main Content Section */}
      {courses.length > 0 && (
        <div className="flex flex-col lg:flex-row mt-10 -mx-4 justify-items-center-safe">
          {/* Chart Container with animation */}
          {result && (
            <div
              className={`w-full lg:w-1/3 shadow-md rounded-lg p-6 flex items-center justify-center
                transition-opacity duration-500 ease-in-out transform
                ${showChart ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-2'}`}
            >
              <div className={`w-full ${getChartHeight()}`}>
                <AttendanceSummaryChart data={result} />
              </div>
            </div>
          )}

          {/* Course List Container */}
          <div className="w-full lg:w-2/3 rounded-lg p-4 pt-7">
            <div className="space-y-3 max-h-[450px] overflow-y-auto">
              {courses.map((course, index) => (
                <button
                  key={index}
                  className={`w-full text-left border border-gray-300 rounded-lg px-4 py-2 shadow-sm font-medium transition-all duration-300
                    ${selectedCourse === course.course_code
                      ? 'bg-green-500 text-white'
                      : 'bg-gray-100 text-gray-800 hover:bg-green-500 hover:text-white'}`}
                  onClick={() => fetchData(registrationNumber, course.course_code)}
                >
                  {course.course_code} - {course.course_name}
                </button>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
