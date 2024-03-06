// Dashboard.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import './form.css'; // Import the CSS file

const Dashboard = () => {
  const location = useLocation();
  const userEmail = location.state?.userEmail || ''; // Get user's email from location state
  const [user, setUser] = useState(null);
  const [balance, setBalance] = useState(null);
  const [error, setError] = useState('');
  const [category, setCategory] = useState('');
  const [amount, setAmount] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [userInfoResponse, balanceResponse] = await Promise.all([
          axios.get(`http://localhost:8086/api/user/info?email=${userEmail}`),
          axios.get(`http://localhost:8086/api/user/getBalance?email=${userEmail}`)
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

    if (userEmail) {
      fetchData();
    }
  }, [userEmail]);

  const handleAddExpense = async () => {
    try {
      await axios.post(`http://localhost:8086/api/user/expenses`, {
        email: userEmail,
        category,
        amount
      });
      setCategory('');
      setAmount('');
      // Refresh balance after adding an expense
      const balanceResponse = await axios.get(`http://localhost:8086/api/user/getBalance?email=${userEmail}`);
      setBalance(balanceResponse.data);
    } catch (error) {
      console.error('Error adding expense:', error);
    }
  };

  return (
    <div className="form-container">
      <h2 className="form-title">Dashboard</h2>
      {error && <div className="error-message">{error}</div>}
      {user && (
        <div>
          <p>Username: {user.username}</p>
          <p>Email: {user.email}</p>
          {balance && <p>Balance: {balance}</p>}
          <form onSubmit={handleAddExpense}>
            <input
              className="form-input"
              type="text"
              placeholder="Category"
              value={category}
              onChange={(e) => setCategory(e.target.value)}
              required
            />
            <input
              className="form-input"
              type="number"
              placeholder="Amount"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              required
            />
            <button className="form-button" type="submit">Add Expense</button>
          </form>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
