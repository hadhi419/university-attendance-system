import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from '../layout/MainLayout';
import Dashboard from '../pages/Dashboard';
import AdminPanel from '../pages/AdminPanel';
import Login from '../pages/Login';
import Record from '../pages/Record';
import PrivateRoute from '../utils/privateRoute';
import Summary from '../pages/DashboardMenu/summary';
import ByDate from '../pages/DashboardMenu/ByDate';
// import Courses from '../pages/DashboardMenu/Courses';


const AppRouter = () => {
  // React state for authentication
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsAuthenticated(!!token);
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        {/* Pass setIsAuthenticated to Login to update auth state after login */}
        <Route path="/login" element={<Login setIsAuthenticated={setIsAuthenticated} />} />

        <Route element={<PrivateRoute isAuthenticated={isAuthenticated} />}>
          <Route path="/" element={<MainLayout />}>
            <Route index element={<Dashboard />} />
            <Route path="admin" element={<AdminPanel />} />
            <Route path="record" element={<Record />} />


            <Route path="DashboardMenu/summary" element={<Summary />} />
            <Route path="DashboardMenu/by-date" element={<ByDate />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
