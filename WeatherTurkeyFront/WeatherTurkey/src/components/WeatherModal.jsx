import React from 'react';

const WeatherModal = ({ data, onClose }) => {
  if (!data || data.error) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="close-button" onClick={onClose}>&times;</button>
        <h2>{data.name}</h2>
        <div className="weather-details">
          <img
            src={`https://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png`}
            alt={data.weather[0].description}
          />
          <p className="temperature">{Math.round(data.main.temp)}°C</p>
          <p className="description">{data.weather[0].description}</p>
          <div className="extra-details">
            <span>Hissedilen: <strong>{Math.round(data.main.feels_like)}°C</strong></span>
            <span>Nem: <strong>%{data.main.humidity}</strong></span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default WeatherModal;