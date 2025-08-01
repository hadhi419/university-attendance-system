import React from 'react';
import { useNavigate } from 'react-router-dom';
 // Assuming you have a utility to manage auth state

const Login = ({ setIsAuthenticated }) => {


  const isAuthenticated = !!localStorage.getItem('token');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
        e.preventDefault();

        const email = e.target.email.value;
        const password = e.target.password.value;

        if (!email || !password) {
          alert("Please fill in all fields");
          return;
        }

        try {
          const response = await fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ username: email, password }),
          });


          console.log("response", response.body);
            
          if (response.ok) {
             const data = await response.json();
              localStorage.setItem('token', data.token);
              setIsAuthenticated(true); // update auth state in AppRouter
              navigate('/');
          } else {
            const errorData = await response.json();
            alert(errorData.message || "Invalid credentials");
          }
        } catch (error) {
          alert("Login failed: " + error.message);
        }
};




  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <form className="bg-white p-6 rounded shadow-md w-96" onSubmit={handleLogin}>
        <h2 className="text-2xl mb-4 font-bold">Login</h2>
        <input
          type="email"
          name="email"
          placeholder="Email"
          className="w-full mb-3 p-2 border rounded"
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          className="w-full mb-4 p-2 border rounded"
        />
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-green-700 hover:rounded-xl transition-all duration-300"
        >
          Login
        </button>
      </form>
    </div>
  );
};

export default Login;
