import React, { useState } from 'react';
import axios from 'axios';
import { FaCheckCircle, FaTimesCircle, FaClock } from 'react-icons/fa';

const AttendanceByDate = () => {
  const [registrationNumber, setRegistrationNumber] = useState('');
  const [date, setDate] = useState('');
  const [attendanceData, setAttendanceData] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const fetchAttendance = async () => {
    if (!registrationNumber.trim()) {
      setError(new Error('Registration number is required'));
      return;
    }
    if (!date.trim()) {
      setError(new Error('Date is required'));
      return;
    }

    setLoading(true);
    setError(null);
    setAttendanceData([]);

    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(
        `http://localhost:8080/attendance/student/${registrationNumber}/by-date`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
            date: date,
          },
        }
      );
      setAttendanceData(response.data);
    } catch (err) {
      console.error(err);
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  const getStatusIcon = (status) => {
    switch (status) {
      case 'Present':
        return <FaCheckCircle className="text-green-600 inline mr-1" />;
      case 'Absent':
        return <FaTimesCircle className="text-red-600 inline mr-1" />;
      case 'Late':
        return <FaClock className="text-yellow-600 inline mr-1" />;
      default:
        return null;
    }
  };

  return (
    <div className="p-6">
      {/* Input fields */}
      <div className="flex flex-col md:flex-row gap-4 mb-4">
        <input
          type="text"
          placeholder="Enter Registration Number"
          className="flex-1 border border-gray-300 px-3 py-2 rounded"
          value={registrationNumber}
          onChange={(e) => setRegistrationNumber(e.target.value)}
        />
        <input
          type="date"
          className="border border-gray-300 px-3 py-2 rounded"
          value={date}
          onChange={(e) => setDate(e.target.value)}
        />
        <button
          onClick={fetchAttendance}
          className="bg-cyan-600 text-white px-4 py-2 rounded hover:bg-cyan-700"
          disabled={loading}
        >
          {loading ? 'Loading...' : 'Fetch Attendance'}
        </button>
      </div>

      {/* Error */}
      {error && (
        <p className="text-red-500 font-medium mb-4">Error: {error.message}</p>
      )}

      {/* Results */}
      {attendanceData.length > 0 && (
        <div className="overflow-x-auto mt-6">
          <table className="min-w-full border border-gray-300">
            <thead className="bg-gray-100">
              <tr>
                <th className="border px-4 py-2">Course Code</th>
                <th className="border px-4 py-2">Course Name</th>
                <th className="border px-4 py-2">Status</th>
              </tr>
            </thead>
            <tbody>
              {attendanceData.map((record, index) => (
                <tr key={index}>
                  <td className="border px-4 py-2">{record.courseCode}</td>
                  <td className="border px-4 py-2">{record.courseName}</td>
                  <td className="border px-4 py-2">
                    {getStatusIcon(record.status)}
                    {record.status}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {attendanceData.length === 0 && !loading && !error && (
        <p className="text-gray-500 mt-4">No attendance records found.</p>
      )}
    </div>
  );
};

export default AttendanceByDate;
