import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import './dashboard.css'; // Import the CSS file

// Header component
const Header = () => (
  <h2 className="dashboard-title">Dashboard</h2>
);

// UserInfo component
const UserInfo = ({ user }) => (
  <div>
    <p>Username: {user.username}</p>
  </div>
);

// Balance component
const Balance = ({ balance }) => (
  <div className="balance">Available Balance: {balance}</div>
);

// ExpensesList component
const ExpensesList = ({ expenses }) => (
  <div className="expenses-list">
    <h3>Expenses:</h3>
    <table className="expense-table">
      <thead>
        <tr>
          <th>Category</th>
          <th>Amount</th>
          <th>Date</th>
        </tr>
      </thead>
      <tbody>
        {expenses.slice(0).reverse().map((expense, index) => (
          <tr key={index} className="expense-item">
            <td>{expense.category}</td>
            <td>{expense.amount}</td>
            <td>{formatDate(expense.expenseDate)}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);


// Function to format date
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date instanceof Date && !isNaN(date) ? `${date.toLocaleDateString()} ${date.toLocaleTimeString()}` : dateString;
};

// DeleteUserButton component
const DeleteUserButton = ({ handleDeleteUser }) => (
  <button onClick={handleDeleteUser} className="delete-user-button">Delete User</button>
);

const Dashboard = () => {
  const location = useLocation();
  const navigate = useNavigate(); // Get the navigate function from useNavigate hook
  const userEmail = location.state?.userEmail || '';
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
      const balanceResponse = await axios.get(`http://localhost:8086/api/user/getBalance?email=${userEmail}`);
      setBalance(balanceResponse.data);
      setSalary(0);
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
      const expensesResponse = await axios.get(`http://localhost:8086/api/expensesByEmail?email=${userEmail}`);
      setExpenses(expensesResponse.data);
      const updatedBalance = balance - parseFloat(expenseAmount);
      setBalance(updatedBalance.toFixed(2));
      setCategory('');
      setExpenseAmount('');
    } catch (error) {
      console.error('Error adding expense:', error);
    }
  };

  const handleDeleteUser = async () => {
    try {
      await axios.delete(`http://localhost:8086/api/user/delete?email=${userEmail}`);
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

  const handleCategoryChange = (e) => {
    setCategory(e.target.value);
  };

  const handleAmountChange = (e) => {
    setExpenseAmount(e.target.value);
  };

  const handleSalaryChange = (e) => {
    setSalary(e.target.value);
  };

  const handleSignOut = () => {
    // Implement sign-out logic here, such as clearing user data from local storage, redirecting to sign-in page, etc.
    navigate('/login'); // Redirect to sign-in page after sign-out
  };

  return (
    <div className="dashboard-container">
      <Header />
      {error && <div className="error-message">{error}</div>}
      {user && (
        <div className="dashboard-content">
          <div className="user-info">
            <UserInfo user={user} />
            <Balance balance={balance} />
          </div>
          <div className="dashboard-actions">
            <div className="expense-form">
              <h3>Add Expense</h3>
              <input
                type="text"
                placeholder="Expense Category"
                value={category}
                onChange={handleCategoryChange}
              />
              <input
                type="number"
                placeholder="Expense Amount"
                value={expenseAmount}
                onChange={handleAmountChange}
              />
              <button onClick={handleAddExpense}>Add Expense</button>
            </div>
            <div className="salary-form">
              <h3>Add Salary</h3>
              <input
                type="number"
                placeholder="Enter Salary"
                value={salary}
                onChange={handleSalaryChange}
              />
              <button onClick={handleAddSalary}>Add Salary</button>
            </div>
          </div>
          <div className="expenses-list">
            <h3>Expenses</h3>
            <ExpensesList expenses={expenses} />
          </div>
          <div className="dashboard-actions">
            <DeleteUserButton handleDeleteUser={handleDeleteUser} />
            <button onClick={handleSignOut} className="sign-out-button">Sign Out</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
