import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from '../layout/MainLayout';
import Dashboard from '../pages/Dashboard';
import AdminPanel from '../pages/AdminPanel';
import Login from '../pages/Login';
import Record from '../pages/Record';

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public routes */}
        <Route path="/login" element={<Login />} />

        {/* Protected routes with layout */}
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Dashboard />} />
          <Route path="admin" element={<AdminPanel />} />
          <Route path="record" element={<Record />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
