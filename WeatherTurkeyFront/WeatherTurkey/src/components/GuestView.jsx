import React from 'react';
import TurkeyMap from './TurkeyMap';

const GuestView = ({ onCityClick }) => {
  return (
    <div className="map-content">
      <h2 className="view-title">Türkiye İnteraktif Hava Durumu Haritası</h2>
      <p className="view-subtitle">Ayrıntılı bilgi için bir il seçin</p>
      <TurkeyMap onProvinceClick={onCityClick} />
    </div>
  );
};

export default GuestView;