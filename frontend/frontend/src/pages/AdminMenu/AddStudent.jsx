import React, { useState } from 'react';
import axios from 'axios';

const RegisterUser = () => {
  const [user, setUser] = useState({
    email: '',
    password: ''
  });

  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');

    try {
      await axios.post('http://localhost:8080/api/register', {
        ...user,
        role: 'student' // Explicitly set or ensure backend defaults to this
      });
      setMessage('Student user registered successfully!');
      setUser({ email: '', password: '' });
    } catch (err) {
      setMessage('Registration failed. Email might already exist.');
      console.error(err);
    }
  };

  return (
    <div className="max-w-sm mx-auto mt-8 bg-white p-6 rounded shadow">
      <h2 className="text-2xl font-bold mb-4">Register Student</h2>
      {message && <p className="mb-4 text-center text-sm text-gray-700">{message}</p>}
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={user.email}
          onChange={handleChange}
          className="w-full p-2 border rounded"
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={user.password}
          onChange={handleChange}
          className="w-full p-2 border rounded"
          required
        />
        <button
          type="submit"
          className="w-full bg-cyan-700 text-white py-2 px-4 rounded hover:bg-green-800 hover:rounded-xl transition-all duration-300"
        >
          Register
        </button>
      </form>
    </div>
  );
};

export default RegisterUser;
