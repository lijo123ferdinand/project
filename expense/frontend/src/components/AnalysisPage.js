// Analysis.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './form.css'; // Import the CSS file

const Analysis = () => {
  const [expenses, setExpenses] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchExpenses = async () => {
      try {
        const response = await axios.get('http://localhost:8086/api/expenses');
        setExpenses(response.data);
        setError('');
      } catch (error) {
        setExpenses([]);
        setError('Error fetching expenses');
        console.error('Expenses fetch error:', error);
      }
    };

    fetchExpenses();
  }, []);

  return (
    <div className="form-container">
      <h2 className="form-title">Analysis</h2>
      {error && <div className="error-message">{error}</div>}
      <table>
        <thead>
          <tr>
            <th>Date</th>
            <th>Category</th>
            <th>Amount</th>
          </tr>
        </thead>
        <tbody>
          {expenses.map((expense) => (
            <tr key={expense.id}>
              <td>{expense.date}</td>
              <td>{expense.category}</td>
              <td>{expense.amount}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Analysis;
