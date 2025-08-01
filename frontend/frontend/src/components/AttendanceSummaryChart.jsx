import React from "react";
import { Pie } from "react-chartjs-2";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

const AttendanceSummaryChart = ({ data }) => {
  const chartData = {
    labels: ["Present", "Absent", "Late"],
    datasets: [
      {
        data: [data.presentCount, data.absentCount, data.lateCount],
        backgroundColor: ["#4CAF50", "#F44336", "#FF9800"],
        borderColor: "#fff",
        borderWidth: 2,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "bottom",
      },
      tooltip: {
        enabled: true,
      },
    },
  };

  return (
    <>
    <div className="min-h-screen bg-gray-100 p-4 flex flex-col items-start">
  <h2 className="text-xl font-semibold mb-4">
    Attendance Summary for {data.first_name} {data.last_name} <br />Subject Code {data.courseCode}
  </h2>
  <div className="p-4 border rounded-2xl shadow-md bg-white w-72 h-80 max-w-md">
    <Pie data={chartData} options={options} />
    <p className="mt-2 text-sm text-gray-500">
      Total Sessions: {data.totalSessions}
    </p>
  </div>
    </div>


    </>
  );
};

export default AttendanceSummaryChart;
