import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './LoginPage.css'; // Bu sayfa için özel stil dosyası

const LoginPage = ({ onLoginSuccess }) => {
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [message, setMessage] = useState('');
    const [isError, setIsError] = useState(false);
    const navigate = useNavigate();

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

        if (!formData.username || !formData.password) {
            setMessage("Lütfen tüm alanları doldurun.");
            setIsError(true);
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', formData);
            
            // Başarılı girişte backend'den gelen JWT'yi localStorage'a kaydet
            localStorage.setItem('token', response.data);

            onLoginSuccess();
            
            setMessage("Giriş başarılı! Ana sayfaya yönlendiriliyorsunuz...");
            setIsError(false);
            
            // 1 saniye sonra ana sayfaya yönlendir
            setTimeout(() => {
                navigate('/');
                window.location.reload(); // Sayfanın yeniden yüklenerek login durumunu almasını sağla
            }, 1000);

        } catch (error) {
            setMessage(error.response?.data || "Giriş sırasında bir hata oluştu.");
            setIsError(true);
            // Başarısız girişte token'ı temizle (varsa)
            localStorage.removeItem('token');
        }
    };

    return (
        <main className="form-container">
            <form className="auth-form" onSubmit={handleSubmit}>
                <h2>Giriş Yap</h2>
                
                <div className="form-group">
                    <label htmlFor="username">Kullanıcı Adı</label>
                    <input type="text" id="username" name="username" value={formData.username} onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label htmlFor="password">Şifre</label>
                    <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />
                </div>

                <button type="submit" className="submit-btn">Giriş Yap</button>

                {message && (
                    <div className={`message ${isError ? 'error' : 'success'}`}>
                        {message}
                    </div>
                )}
            </form>
        </main>
    );
};

export default LoginPage;