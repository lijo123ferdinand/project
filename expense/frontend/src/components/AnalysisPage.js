import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Chart from 'chart.js/auto';
import './AnalysisPage.css';

const AnalysisPage = ({ loggedInUser }) => {
  const [expenses, setExpenses] = useState([]);
  const [startDate, setStartDate] = useState(new Date(1970, 0, 1).toISOString().slice(0, 10)); // Start date set to 1970-01-01
  const [endDate, setEndDate] = useState(new Date().toISOString().slice(0, 10)); // End date set to today
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchExpenses = async () => {
      try {
        const response = await axios.get(`http://localhost:8086/api/user/expensesByDateRange?email=${loggedInUser}&startDate=${startDate}&endDate=${endDate}`);
        setExpenses(response.data);
        setError('');
        renderChart(response.data); // Render chart with fetched expenses
      } catch (error) {
        setExpenses([]);
        setError('Error fetching expenses');
        console.error('Expenses fetch error:', error);
      }
    };

    if (loggedInUser) {
      fetchExpenses();
    }
  }, [loggedInUser, startDate, endDate]);

  const handleDateRangeSubmit = async (e) => {
    e.preventDefault();
    try {
      // Set start date to beginning of the day and end date to end of the day
      const formattedStartDate = new Date(startDate).toISOString();
      const formattedEndDate = new Date(endDate).toISOString();
  
      const response = await axios.get(`http://localhost:8086/api/user/expensesByDateRange?email=${loggedInUser}&startDate=${formattedStartDate}&endDate=${formattedEndDate}`);
      setExpenses(response.data);
      setError('');
      renderChart(response.data); // Render chart with fetched expenses
    } catch (error) {
      setExpenses([]);
      setError('Error fetching expenses for the specified date range');
      console.error('Date range fetch error:', error);
    }
  };

  const renderChart = (expensesData) => {
    const ctx = document.getElementById('expensesChart');
    const categories = {};
    expensesData.forEach(expense => {
      if (categories[expense.category]) {
        categories[expense.category] += parseFloat(expense.amount);
      } else {
        categories[expense.category] = parseFloat(expense.amount);
      }
    });

    const labels = Object.keys(categories);
    const data = Object.values(categories);

    new Chart(ctx, {
      type: 'pie',
      data: {
        labels,
        datasets: [{
          label: 'Expenses',
          data,
          backgroundColor: [
            'rgba(255, 99, 132, 0.6)',
            'rgba(54, 162, 235, 0.6)',
            'rgba(255, 206, 86, 0.6)',
            'rgba(75, 192, 192, 0.6)',
            'rgba(153, 102, 255, 0.6)',
            'rgba(255, 159, 64, 0.6)'
          ]
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false
      }
    });
  };

  return (
    <div className="analysis-container">
      <h2 className="analysis-title">Expense Analysis</h2>
      <form onSubmit={handleDateRangeSubmit} className="date-range-form">
        <div className="form-group">
          <label htmlFor="startDate">Start Date:</label>
          <input
            type="date"
            id="startDate"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
            required
            className="form-control"
          />
        </div>
        <div className="form-group">
          <label htmlFor="endDate">End Date:</label>
          <input
            type="date"
            id="endDate"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
            required
            className="form-control"
          />
        </div>
        <button type="submit" className="btn btn-primary">Get Expenses</button>
      </form>
      {error && <div className="error-message">{error}</div>}
      <div className="expense-list">
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
      <div className="chart-container">
        <canvas id="expensesChart" width="400" height="400"></canvas>
      </div>
    </div>
  );
};
export default AnalysisPage;
