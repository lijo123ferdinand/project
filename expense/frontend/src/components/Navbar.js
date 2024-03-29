// Navbar.js
import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'; // Import the CSS file

const Navbar = () => {
  return (
    <nav className="navbar">
      <ul className="navbar-list">
        {/* Remove the Login link from the navbar */}
        <li className="navbar-item">
          <Link to="/dashboard" className="navbar-link">Dashboard</Link>
        </li>
        <li className="navbar-item">
          <Link to="/analysis" className="navbar-link">Analysis</Link>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
