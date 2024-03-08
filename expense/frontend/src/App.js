import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import LoginPage from './components/Login';
import SignupPage from './components/Signup';
import DashboardPage from './components/Dashboard';
import AnalysisPage from './components/AnalysisPage';

const App = () => {
  const [loggedInUser, setLoggedInUser] = useState(null);

  return (
    <Router>
      <div>
        <Navbar />
        <Routes>
          <Route path="/login" element={<LoginPage setLoggedInUser={setLoggedInUser} />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/analysis" element={<AnalysisPage loggedInUser={loggedInUser} />} /> 
          <Route path="/" element={<Navigate to="/login" />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
