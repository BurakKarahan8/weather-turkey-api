import React, { useState, useEffect } from 'react';
import apiClient from '../api/axiosConfig';
import GuestView from '../components/GuestView';
import UserDashboard from '../components/UserDashboard';
import WeatherModal from '../components/WeatherModal';
import './HomePage.css';

const HomePage = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [dashboardData, setDashboardData] = useState(null);
  const [modalData, setModalData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkAuthAndFetchData = async () => {
      const token = localStorage.getItem('token');
      if (token) {
        setIsLoggedIn(true);
        try {
          const response = await apiClient.get('/api/dashboard');
          setDashboardData(response.data);
        } catch (error) {
          console.error("Dashboard verisi alınamadı:", error);
          if (error.response && (error.response.status === 401 || error.response.status === 403)) {
            localStorage.removeItem('token');
            setIsLoggedIn(false);
          }
        }
      } else {
        setIsLoggedIn(false);
      }
      setIsLoading(false);
    };
    checkAuthAndFetchData();
  }, []);

  const handleMapCityClick = async (city) => {
    setIsLoading(true);
    setModalData(null);
    try {
      const response = await apiClient.get(`/api/public/weather?city=${city}`);
      setModalData(response.data);
    } catch (error) {
      console.error(`${city} için hava durumu alınamadı!`, error);
      alert(`${city} için hava durumu bilgisi alınamadı.`);
    } finally {
      setIsLoading(false);
    }
  };

  if (isLoading) {
    return <div className="loading-spinner">Yükleniyor...</div>;
  }

  return (
    <main className="homepage-main-content">
      {isLoggedIn ? (
        <UserDashboard dashboardData={dashboardData} />
      ) : (
        <GuestView onCityClick={handleMapCityClick} />
      )}
      
      {isLoading && <div className="loading-spinner">Yükleniyor...</div>}
      <WeatherModal data={modalData} onClose={() => setModalData(null)} />
    </main>
  );
};

export default HomePage;