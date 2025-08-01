import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { FiMenu, FiX } from 'react-icons/fi';
import { FaChevronDown, FaChevronUp } from 'react-icons/fa';

const Sidebar = () => {
  const [collapsed, setCollapsed] = useState(false);
  const [dashboardOpen, setDashboardOpen] = useState(false);
  const [adminOpen, setadminOpen] = useState(false);

  const toggleSidebar = () => {
    setCollapsed(!collapsed);
  };

  const toggleDashboardMenu = () => {
    setDashboardOpen(!dashboardOpen);
  };

    const toggleAdminMenu = () => {
    setadminOpen(!adminOpen);
  };


  return (
    <aside
      className={`bg-cyan-800 text-white p-4 transition-all duration-300 ${
        collapsed ? 'w-16' : 'w-64'
      }`}
    >
      <div className="flex items-center justify-between mb-4">
        {!collapsed && <h2 className="text-2xl font-bold">Admin Panel</h2>}
        <button onClick={toggleSidebar} className="text-white p-2">
          {collapsed ? <FiMenu size={20} /> : <FiX size={20} />}
        </button>
      </div>

      {!collapsed && (
        <nav className="flex flex-col gap-3">
          {/* Dashboard main link with submenu toggle */}

          <Link
            to="/home"
            className="hover:underline"
          >
            Home Page
          </Link>
          <div>
            <button
              onClick={toggleDashboardMenu}
              className="flex justify-between items-center w-full hover:underline"
            >
        
              <span>Dashboard</span>
              {dashboardOpen ? <FaChevronUp size={12} /> : <FaChevronDown size={12} />}
            </button>

            {/* Dashboard submenu */}
            {dashboardOpen && (
              <div className="ml-4 mt-2 flex flex-col gap-2 text-sm">
                <Link to="/DashboardMenu/summary" className="hover:underline">Attendance Summary</Link>
                <Link to="DashboardMenu/AttendanceByDate" className="hover:underline">Attendance by Date</Link>
                <Link to="/DashboardMenu/AttendanceByCourse" className="hover:underline">Course Attendance</Link>
              </div>
            )}
          </div>


          <div>
            <button
              onClick={toggleAdminMenu}
              className="flex justify-between items-center w-full hover:underline"
            >
            <span>Add Users</span>
              {dashboardOpen ? <FaChevronUp size={12} /> : <FaChevronDown size={12} />}
            </button>

            {/* Dashboard submenu */}
            {adminOpen && (
              <div className="ml-4 mt-2 flex flex-col gap-2 text-sm">
                <Link to="/AdminMenu/AddStudent" className="hover:underline">Add Student</Link>
                <Link to="/AdminMenu/AddAdmin" className="hover:underline">Add Admin</Link>
                {/* <Link to="/DashboardMenu/AttendanceByCourse" className="hover:underline">Add other staff</Link> */}
              </div>
            )}
          </div>

          {/* Other main links */}
          {/* <Link to="/admin" className="hover:underline">Admin Panel</Link> */}
          <Link to="/record" className="hover:underline">Record</Link>
          <Link
            to="/login"
            className="hover:underline"
            onClick={() => localStorage.removeItem('token')}
          >
            Logout
          </Link>
        </nav>
      )}
    </aside>
  );
};

export default Sidebar;
