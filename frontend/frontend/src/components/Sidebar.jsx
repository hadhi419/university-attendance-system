import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { FiMenu, FiX } from 'react-icons/fi';

const Sidebar = () => {
  const [collapsed, setCollapsed] = useState(false);

  const toggleSidebar = () => {
    setCollapsed(!collapsed);
  };

  return (
    <aside
      className={`bg-cyan-800 text-white p-4 transition-all duration-300 ${
        collapsed ? 'w-16' : 'w-64'
      }`}
    >
      <div className="flex items-center justify-between mb-4">
        {!collapsed && (
          <h2 className="text-2xl font-bold">Admin Panel</h2>
        )}
        <button
          onClick={toggleSidebar}
          className="text-white p-2 flex items-center justify-center"
        >
          {collapsed ? <FiMenu size={20} /> : <FiX size={20} />}
        </button>
      </div>

      {!collapsed && (
        
        <nav className="flex flex-col gap-3">
          <Link to="/" className="hover:underline">
            Dashboard
          </Link>
          <Link to="/admin" className="hover:underline">
            Admin Panel
          </Link>
          <Link to="/record" className="hover:underline">
            Record
          </Link>
          <Link to="/login" className="hover:underline" onClick={() => localStorage.removeItem('token')}>
            Logout
          </Link>
          
        </nav>
      )}
    </aside>
  );
};

export default Sidebar;
