import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080', // Backend adresimiz
});

// Bu 'interceptor', her istek gönderilmeden önce araya girer
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      // Eğer token varsa, Authorization başlığına 'Bearer ' ön ekiyle ekle
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default apiClient;