import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from '../layout/MainLayout';
import Dashboard from '../pages/Dashboard';
import HomePage from '../pages/homePage';
import AdminPanel from '../pages/AdminPanel';
import Login from '../pages/Login';
import Record from '../pages/Record';
import PrivateRoute from '../utils/privateRoute';
import Summary from '../pages/DashboardMenu/summary';
import ByDate from '../pages/DashboardMenu/ByDate';
import AttendanceByCourse from '../pages/DashboardMenu/AttendanceByCourse';
import AddStudent from '../pages/AdminMenu/AddStudent';
import AddAdmin from '../pages/AdminMenu/AddAdmin';
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
            <Route index element={<HomePage />} />
            <Route path="admin" element={<AdminPanel />} />
            <Route path="record" element={<Record />} />


            <Route path="DashboardMenu/summary" element={<Summary />} />
            <Route path="/home" element={<HomePage />} />
            <Route path="DashboardMenu/AttendanceByDate" element={<ByDate />} />
            <Route path="DashboardMenu/AttendanceByCourse" element={<AttendanceByCourse />} />
            <Route path="AdminMenu/AddStudent" element={<AddStudent />} />
            <Route path="AdminMenu/AddAdmin" element={<AddAdmin />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
