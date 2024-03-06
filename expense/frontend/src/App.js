// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import LoginPage from './components/Login';
import SignupPage from './components/Signup';
import DashboardPage from './components/Dashboard';
import AnalysisPage from './components/AnalysisPage'; // Import the Analysis component
import { Navigate } from 'react-router-dom';


const App = () => {
  return (
    <Router>
      <div>
        <Navbar />
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/analysis" element={<AnalysisPage />} /> 
          <Route path="/" element={<Navigate to="/login" />} />

        </Routes>
      </div>
    </Router>
  );
};

export default App;
