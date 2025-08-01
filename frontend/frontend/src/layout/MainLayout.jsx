import React from 'react';
import Navbar from '../components/Navbar';
import Sidebar from '../components/Sidebar';
import { Outlet } from 'react-router-dom';
import AttendanceRecorder from '../components/AttendanceRecorder';

const MainLayout = () => {
  return (
     <div className="flex flex-col h-screen">
      {/* Navbar spans the full width */}
      <Navbar />

      {/* Content area: sidebar + main */}
      <div className="flex flex-1">
        <Sidebar />
        <main className="flex-1 p-4 overflow-y-auto bg-gray-100">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default MainLayout;
