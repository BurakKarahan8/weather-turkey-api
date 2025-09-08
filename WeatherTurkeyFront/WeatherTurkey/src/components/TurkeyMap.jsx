import React from 'react';
import { turkeyMapData } from '../data/mapData'; // Veri dosyasını import et

const TurkeyMap = ({ onProvinceClick }) => {
  const handleMapClick = (event) => {
    const provinceName = event.target.getAttribute('data-name');
    if (provinceName && onProvinceClick) {
      onProvinceClick(provinceName);
    }
  };

  return (
    <svg
      className="turkey-map"
      xmlns="http://www.w3.org/2000/svg"
      // ÖNEMLİ: width ve height kaldırıldı, sadece viewBox oranı korumak için kaldı.
      viewBox="0 0 1024 453.71"
      onClick={handleMapClick}
    >
      {turkeyMapData.map((province) => (
        <path
          key={province.id}
          id={province.id}
          d={province.path}
          data-name={province.name}
          title={province.name}
        />
      ))}
    </svg>
  );
};

export default TurkeyMap;