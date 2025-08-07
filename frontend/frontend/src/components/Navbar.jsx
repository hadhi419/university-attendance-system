import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar = ({ setIsAuthenticated }) => {
  const navigate = useNavigate(); // ✅ Now you can use `navigate`

  const handleLogout = (e) => {
    e.preventDefault();
    localStorage.removeItem('token');
    setIsAuthenticated(false); // ✅ make sure this is passed in from parent
    navigate('/login'); // ✅ works now
  };

  return (
    <header className="bg-cyan shadow px-4 py-3 w-full flex justify-between items-center h-15">
      <h1 className="text-xl font-bold text-cyan-800">Attendance System</h1>
      <ul className="flex space-x-5">
        <li>
          <Link to="/" className="text-blue-600 hover:underline">Dashboard</Link>
        </li>
        <li>
          <Link to="/admin" className="text-blue-600 hover:underline">Admin Panel</Link>
        </li>
        <li>
          <button onClick={handleLogout} className="text-blue-600 hover:underline bg-none border-none p-0 text-left">
            Logout
          </button>
        </li>
      </ul>
    </header>
  );
};

export default Navbar;
