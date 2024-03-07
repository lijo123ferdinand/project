import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import './dashboard.css'; // Import the CSS file

const Dashboard = () => {
  const location = useLocation();
  const userEmail = location.state?.userEmail || ''; // Get user's email from location state
  const [user, setUser] = useState(null);
  const [balance, setBalance] = useState(null);
  const [expenses, setExpenses] = useState([]);
  const [error, setError] = useState('');
  const [salary, setSalary] = useState(0);
  const [category, setCategory] = useState('');
  const [expenseAmount, setExpenseAmount] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [userInfoResponse, balanceResponse, expensesResponse] = await Promise.all([
          axios.get(`http://localhost:8086/api/user/info?email=${userEmail}`),
          axios.get(`http://localhost:8086/api/user/getBalance?email=${userEmail}`),
          axios.get(`http://localhost:8086/api/expensesByEmail?email=${userEmail}`)
        ]);

        setUser(userInfoResponse.data);
        setBalance(balanceResponse.data);
        setExpenses(expensesResponse.data);
        setError('');
      } catch (error) {
        setUser(null);
        setBalance(null);
        setExpenses([]);
        setError('Error fetching user information');
        console.error('User info fetch error:', error);
      }
    };

    if (userEmail) {
      fetchData();
    }
  }, [userEmail]);

  const handleAddSalary = async () => {
    try {
      await axios.post(`http://localhost:8086/api/user/addSalary`, {
        email: userEmail,
        amount: salary
      });
      // Refresh balance after adding salary
      const balanceResponse = await axios.get(`http://localhost:8086/api/user/getBalance?email=${userEmail}`);
      setBalance(balanceResponse.data);
      setSalary(0); // Reset salary input
    } catch (error) {
      console.error('Error adding salary:', error);
    }
  };

  const handleAddExpense = async () => {
    try {
      await axios.post(`http://localhost:8086/api/user/expenses`, {
        email: userEmail,
        category,
        amount: expenseAmount
      });
      // Refresh expenses after adding expense
      const expensesResponse = await axios.get(`http://localhost:8086/api/expensesByEmail?email=${userEmail}`);
      setExpenses(expensesResponse.data);
      setCategory('');
      setExpenseAmount('');
    } catch (error) {
      console.error('Error adding expense:', error);
    }
  };

  const handleDeleteUser = async () => {
    try {
      await axios.delete(`http://localhost:8086/api/user/delete?email=${userEmail}`);
      // Redirect to login page after deleting the user
      // You can use your preferred way to handle redirects
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

  return (
    <div className="dashboard-container">
      <h2 className="dashboard-title">Dashboard</h2>
      {error && <div className="error-message">{error}</div>}
      {user && (
        <div>
          <p>Username: {user.username}</p>
          <div className="balance">Available Balance: {balance}</div>
          <div className="expenses-list">
            <h3>Expenses:</h3>
            <ul>
              {expenses.map((expense, index) => (
                <li key={index} className="expense-item">
                  <p><span className="label">Category:</span> {expense.category}</p>
                  <p><span className="label">Amount:</span> {expense.amount}</p>
                  <p><span className="label">Date:</span> {expense.date}</p>
                </li>
              ))}
            </ul>
          </div>
          <div className="expense-form">
            <input
              type="text"
              placeholder="Expense Category"
              value={category}
              onChange={(e) => setCategory(e.target.value)}
            />
            <input
              type="number"
              placeholder="Expense Amount"
              value={expenseAmount}
              onChange={(e) => setExpenseAmount(e.target.value)}
            />
            <button onClick={handleAddExpense}>Add Expense</button>
          </div>
          <div className="salary-form">
            <input
              type="number"
              placeholder="Enter Salary"
              value={salary}
              onChange={(e) => setSalary(e.target.value)}
            />
            <button onClick={handleAddSalary}>Add Salary</button>
          </div>
          <button onClick={handleDeleteUser} className="delete-user-button">Delete User</button>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
