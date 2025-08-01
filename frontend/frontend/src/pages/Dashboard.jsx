import React, { useEffect, useState } from 'react';
import axios from 'axios'; // ✅ Fix typo: use 'axios'
import AttendanceSummaryChart from '../components/AttendanceSummaryChart';

const Dashboard = () => {
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(true); 
  const [error, setError] = useState(null);
  const [registrationNumber, setRegistrationNumber] = useState('');


    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/attendance/student/${registrationNumber}/course/IT2214`);
        setResult(response.data);
      } catch (err) {
        console.error("Error fetching data:", err);
        setError(err);
      } finally {
        setLoading(false);
      } 
    }
    


  if (error) return <p>Error loading data: {error.message}</p>;

return (
  <>
    <div className="mx-6 flex border border-gray-300 rounded overflow-hidden w-130">
        <input
          type="text"
          placeholder="Registration Number" 
          className="flex-1 px-3 py-2 outline-none"
          value={registrationNumber}
          onChange={(e) => setRegistrationNumber(e.target.value)}  
        />
        <button
          className="bg-cyan-500 text-white px-4 hover:bg-cyan-600 transition"
          onClick={fetchData}
        >
          Fetch Students
        </button>
      </div>
    <div className="flex items-center justify-grid p-4 bg-gray-100 shadow-md">
      
      <div className="p-4">
        <h1 className="text-2xl font-bold mb-4">Dashboard</h1>
        <div className="bg-white shadow-md rounded-lg p-2">
          {result && <AttendanceSummaryChart data={result} />}
        </div>
      </div>
    </div>
  </>
);
}

export default Dashboard;
