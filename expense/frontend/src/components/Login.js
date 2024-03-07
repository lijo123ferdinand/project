import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './form.css'; // Import the CSS file

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8086/api/login', {
        email,
        password
      });
      console.log(response.data);
      navigate('/dashboard', { state: { userEmail: email } }); // Redirect to dashboard with user's email
    } catch (error) {
      setError('Invalid email or password');
      console.error('Login error:', error);
    }
  };

  const handleSignupClick = () => {
    navigate('/signup');
  };

  return (
    <div className="form-container">
      <h2 className="form-title">Login</h2>
      <form onSubmit={handleSubmit}>
        <input
          className="form-input"
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          className="form-input"
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        {error && <div className="error-message">{error}</div>}
        <button className="form-button" type="submit">Login</button>
      </form>
      <p>Don't have an account? <span onClick={handleSignupClick} className="signup-link">Signup</span></p>
    </div>
  );
};

export default Login;