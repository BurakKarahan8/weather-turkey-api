import React from 'react';

const UserDashboard = ({ dashboardData }) => {
  // Veri henüz yüklenmediyse bir yükleniyor mesajı göster
  if (!dashboardData) {
    return <div className="loading-spinner">Panel verileri yükleniyor...</div>;
  }

  const { userCityWeather, regionCitiesWeather } = dashboardData;

  // Ana şehir verisi yoksa bir hata mesajı göster (örn: API hatası)
  if (!userCityWeather) {
      return <div>Panel verileri yüklenirken bir sorun oluştu.</div>
  }

  return (
    <div className="dashboard-container">
      <h2 className="view-title">Kişisel Hava Durumu Panelim</h2>
      <div className="dashboard-grid">
        <div className="main-card">
          <p className="card-subtitle">Şehrim</p>
          <h3 className="card-title">{userCityWeather.cityName}</h3>
          <img
            className="main-weather-icon"
            src={`https://openweathermap.org/img/wn/${userCityWeather.icon}@4x.png`}
            alt={userCityWeather.description}
          />
          <p className="main-temp">{Math.round(userCityWeather.temperature)}°C</p>
          <p className="main-desc">{userCityWeather.description}</p>
        </div>
        <div className="region-cities-list">
          <h4>Bölgenizdeki Diğer Şehirler</h4>
          <ul>
            {regionCitiesWeather && regionCitiesWeather.map(city => (
              <li key={city.cityName}>
                <span className="city-name">{city.cityName}</span>
                <span className="city-temp">{Math.round(city.temperature)}°C</span>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default UserDashboard;