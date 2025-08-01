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
        position: "top",
      },
      tooltip: {
        enabled: true,
      },
    },
  };

  return (
    <>
    <div className="bg-green-400 border-4  p-4 flex flex-col w-72 h-78  rounded-3xl txt  items-center justify-center">
          <h2 className="text-black">{data.course_code}</h2>
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
