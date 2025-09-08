import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './RegisterPage.css';

const RegisterPage = () => {
    const [cities, setCities] = useState([]);
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        cityName: ''
    });
    const [message, setMessage] = useState('');
    const [isError, setIsError] = useState(false);
    const navigate = useNavigate();

    // Component yüklendiğinde şehirleri backend'den çek
    useEffect(() => {
        const fetchCities = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/public/cities');
                setCities(response.data);
                // İlk şehri otomatik olarak seçili hale getir
                if (response.data.length > 0) {
                    setFormData(prev => ({ ...prev, cityName: response.data[0].name }));
                }
            } catch (error) {
                console.error("Şehirler yüklenemedi:", error);
                setMessage("Şehirler yüklenemedi. Lütfen daha sonra tekrar deneyin.");
                setIsError(true);
            }
        };
        fetchCities();
    }, []); // Boş dependency array, bu etkinin sadece bir kez çalışmasını sağlar

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('');
        setIsError(false);

        if (!formData.username || !formData.email || !formData.password || !formData.cityName) {
            setMessage("Lütfen tüm alanları doldurun.");
            setIsError(true);
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/auth/register', formData);
            setMessage(response.data + " Giriş sayfasına yönlendiriliyorsunuz...");
            setIsError(false);
            // Başarılı kayıt sonrası 2 saniye bekle ve login sayfasına yönlendir
            setTimeout(() => {
                navigate('/login');
            }, 2000);
        } catch (error) {
            setMessage(error.response?.data || "Kayıt sırasında bir hata oluştu.");
            setIsError(true);
        }
    };

    return (
        <main className="form-container">
            <form className="auth-form" onSubmit={handleSubmit}>
                <h2>Hesap Oluştur</h2>
                
                <div className="form-group">
                    <label htmlFor="username">Kullanıcı Adı</label>
                    <input type="text" id="username" name="username" value={formData.username} onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label htmlFor="email">E-posta Adresi</label>
                    <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label htmlFor="password">Şifre</label>
                    <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />
                </div>
                
                <div className="form-group">
                    <label htmlFor="cityName">Şehir</label>
                    <select id="cityName" name="cityName" value={formData.cityName} onChange={handleChange} required>
                        {cities.map(city => (
                            <option key={city.name} value={city.name}>{city.name}</option>
                        ))}
                    </select>
                </div>

                <button type="submit" className="submit-btn">Kayıt Ol</button>

                {message && (
                    <div className={`message ${isError ? 'error' : 'success'}`}>
                        {message}
                    </div>
                )}
            </form>
        </main>
    );
};

export default RegisterPage;