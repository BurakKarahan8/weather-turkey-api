import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Navbar.css';

const Navbar = ({ isLoggedIn, onLogout }) => {
  const navigate = useNavigate();

  const handleLogoutClick = () => {
    onLogout(); 
    navigate('/'); 
  };

  return (
    <nav className="navbar">
      <Link to="/" className="nav-logo">WeatherTurkey</Link>
      <div className="nav-links">
        {isLoggedIn ? (
          <>
            <Link to="/dashboard" className="nav-link">Ayarlar</Link>
            <button onClick={handleLogoutClick} className="nav-link-primary logout-btn">Çıkış Yap</button>
          </>
        ) : (
          <>
            <Link to="/login" className="nav-link">Giriş Yap</Link>
            <Link to="/register" className="nav-link-primary">Kayıt Ol</Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;