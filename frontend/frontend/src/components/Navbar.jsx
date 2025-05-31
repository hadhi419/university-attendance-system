import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <header className="bg-cyan shadow px-4 py-3 w-full flex justify-between items-center h-15">
      <h1 className="text-xl  font-bold text-cyan-800 "  >Attendance System</h1>
        <ul className="flex space-x-5 ">
          <li>
            <Link to="/" className="text-blue-600 hover:underline">Dashboard</Link>
          </li>
          <li>
            <Link to="/admin" className="text-blue-600 hover:underline">Admin Panel</Link>
          </li>
          <li>
            <Link to="/login" className="text-blue-600 hover:underline">Logout</Link>
          </li>
        </ul>
    </header>
  );
};

export default Navbar;
