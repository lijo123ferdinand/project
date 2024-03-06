// Dashboard.js
import React, { useState } from 'react';
import axios from 'axios';
import './form.css'; // Import the CSS file

const Dashboard = () => {
  const [email, setEmail] = useState('');
  const [user, setUser] = useState(null);
  const [balance, setBalance] = useState(null);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const [userInfoResponse, balanceResponse] = await Promise.all([
        axios.get(`http://localhost:8086/api/user/info?email=${email}`),
        axios.get(`http://localhost:8086/api/user/getBalance?email=${email}`)
      ]);

      setUser(userInfoResponse.data);
      setBalance(balanceResponse.data);
      setError('');
    } catch (error) {
      setUser(null);
      setBalance(null);
      setError('Error fetching user information');
      console.error('User info fetch error:', error);
    }
  };

  return (
    <div className="form-container">
      <h2 className="form-title">Dashboard</h2>
      <form onSubmit={handleSubmit}>
        <input
          className="form-input"
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <button className="form-button" type="submit">Fetch User Data</button>
      </form>
      {error && <div className="error-message">{error}</div>}
      {user && (
        <div>
          <p>Username: {user.username}</p>
          <p>Email: {user.email}</p>
          {balance && <p>Balance: {balance}</p>}
          {/* Display other user information as needed */}
        </div>
      )}
    </div>
  );
};

export default Dashboard;
