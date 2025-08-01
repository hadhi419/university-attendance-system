import React, { useState } from 'react';
import Navbar from '../components/Navbar';
import Sidebar from '../components/Sidebar';
import { Outlet } from 'react-router-dom';

const MainLayout = () => {
  const [collapsed, setCollapsed] = useState(false);

  return (
    <div className="flex flex-col h-screen">
      {/* Navbar stays at the top */}
      <Navbar />

      {/* Layout with sidebar and main content */}
      <div className="flex flex-1 overflow-hidden">
        <Sidebar collapsed={collapsed} setCollapsed={setCollapsed} />

        {/* Main content area scrolls independently */}
        <main
          className={`flex-1 bg-neutral-50 transition-all duration-300 ${
            collapsed ? 'ml-2' : 'ml-2'
          }`}
        >
          <div className="h-full overflow-y-auto p-4">
            <Outlet />
          </div>
        </main>
      </div>
    </div>
  );
};

export default MainLayout;
