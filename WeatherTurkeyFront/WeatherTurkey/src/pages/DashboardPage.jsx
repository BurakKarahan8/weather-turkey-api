import React, { useState, useEffect } from 'react';
import apiClient from '../api/axiosConfig';
import { useNavigate } from 'react-router-dom';
import './DashboardPage.css'; // Bu sayfa için özel stil dosyası

const DashboardPage = () => {
    const [cities, setCities] = useState([]);
    const [selectedCity, setSelectedCity] = useState('');
    const [message, setMessage] = useState('');
    const [isError, setIsError] = useState(false);
    const navigate = useNavigate();

    // Component yüklendiğinde tüm şehirleri backend'den çek
    useEffect(() => {
        const fetchCities = async () => {
            try {
                const response = await apiClient.get('/api/public/cities');
                setCities(response.data);
                if (response.data.length > 0) {
                    setSelectedCity(response.data[0].name);
                }
            } catch (error) {
                console.error("Şehirler yüklenemedi:", error);
            }
        };
        fetchCities();
    }, []);

    const handleCityChange = (e) => {
        setSelectedCity(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('');
        setIsError(false);

        try {
            const response = await apiClient.put('/api/user/city', { newCityName: selectedCity });
            setMessage(response.data + " Ana sayfaya yönlendiriliyorsunuz...");
            setIsError(false);
            
            // Başarılı güncelleme sonrası 2 saniye bekle ve ana sayfaya yönlendir
            setTimeout(() => {
                navigate('/');
                window.location.reload(); // Ana sayfanın yeni verilerle yüklenmesini sağla
            }, 2000);

        } catch (error) {
            setMessage(error.response?.data || "Şehir güncellenirken bir hata oluştu.");
            setIsError(true);
        }
    };

    return (
        <main className="dashboard-page-container">
            <div className="settings-card">
                <h2>Profil Ayarları</h2>
                <p>Kayıtlı şehrinizi buradan güncelleyebilirsiniz. Ana paneliniz seçtiğiniz yeni şehre göre güncellenecektir.</p>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="city-select">Şehrinizi Seçin</label>
                        <select id="city-select" value={selectedCity} onChange={handleCityChange}>
                            {cities.map(city => (
                                <option key={city.name} value={city.name}>{city.name}</option>
                            ))}
                        </select>
                    </div>
                    <button type="submit" className="submit-btn">Şehri Güncelle</button>
                    {message && (
                        <div className={`message ${isError ? 'error' : 'success'}`}>
                            {message}
                        </div>
                    )}
                </form>
            </div>
        </main>
    );
};

export default DashboardPage;