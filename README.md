# İnteraktif Hava Durumu Portalı

Bu proje, modern web teknolojileri kullanılarak geliştirilmiş, kullanıcılara anlık ve kişiselleştirilmiş hava durumu verileri sunan tam kapsamlı (full-stack) bir web uygulamasıdır. Proje, iki ana bölümden oluşmaktadır:

*   **Backend (API):** Spring Boot ile geliştirilmiş, veritabanı işlemlerini, kullanıcı kimlik doğrulamasını ve OpenWeatherMap API'si ile iletişimi yöneten RESTful servis.
*   **Frontend (UI):** React (Vite ile) kullanılarak geliştirilmiş, misafirler için interaktif bir harita ve kayıtlı kullanıcılar için kişiselleştirilmiş bir panel sunan dinamik bir kullanıcı arayüzü.

---

## ✨ Temel Özellikler

*   **Çift Taraflı Kullanıcı Deneyimi:**
    *   **Misafir Modu:** Giriş yapmamış kullanıcılar, interaktif bir SVG Türkiye haritası üzerinden istedikleri ilin anlık hava durumunu görüntüleyebilir.
    *   **Kullanıcı Modu:** Kayıtlı kullanıcılar, kendi seçtikleri şehre ve o şehrin ait olduğu coğrafi bölgeye özel, zengin içerikli bir "Dashboard" (Ana Panel) ile karşılanır.
*   **Modern Güvenlik Altyapısı (Backend):**
    *   **JWT (JSON Web Token):** Güvenli ve "stateless" (durumsuz) kimlik doğrulama.
    *   **Spring Security:** Rol tabanlı yetkilendirme altyapısı, şifre hash'leme (BCrypt) ve merkezi güvenlik yapılandırması.
*   **Dinamik ve İnteraktif Arayüz (Frontend):**
    *   **React & Vite:** Hızlı ve modern bir geliştirme deneyimi.
    *   **SVG Harita:** Harici kütüphanelere bağımlı olmayan, yüksek performanslı ve tamamen özelleştirilebilir bir harita.
    *   **Dinamik Yönlendirme:** `react-router-dom` ile kullanıcı durumuna göre değişen, çok sayfalı bir uygulama yapısı.
*   **Sağlam Altyapı:**
    *   **PostgreSQL & Docker:** Güvenilir ve taşınabilir bir veritabanı altyapısı.
    *   **Katmanlı Mimari:** Sürdürülebilir ve yönetilebilir bir kod tabanı.
    *   **Merkezi Hata Yönetimi:** Backend'de `@ControllerAdvice` ile tutarlı hata cevapları.

## 🛠️ Kullanılan Teknolojiler

| Kategori | Teknoloji |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot 3+, Spring Security, Spring Data JPA, jjwt |
| **Frontend** | React 18+, Vite, JavaScript (ES6+), Axios, React Router |
| **Veritabanı**| PostgreSQL 15 |
| **Ortam** | Docker |
| **Genel** | Maven, Node.js (npm), Lombok, Git & GitHub |

## 🚀 Kurulum ve Çalıştırma

Bu projeyi yerel makinenizde çalıştırmak için aşağıdaki adımları izleyin.

### Ön Koşullar
*   Java JDK 17+
*   Apache Maven
*   Node.js 18+
*   Docker Desktop

---

### **1. Backend (API) Kurulumu**

#### a) Veritabanını Başlatma
Projenin bir PostgreSQL veritabanına ihtiyacı vardır. Projenin ana dizininde bir terminal açın ve aşağıdaki Docker komutunu çalıştırın:
```bash
docker run --name weather-app-postgres -e POSTGES_USER=burak -e POSTGRES_PASSWORD=12345 -e POSTGRES_DB=weatherdb -p 5434:5432 -d postgres:15
```

#### b) API Anahtarını Ayarlama
Backend'in OpenWeatherMap'ten veri çekebilmesi için bir API anahtarına ihtiyacı vardır.
1.  `WeatherTurkey-api/src/main/resources/application.yml` dosyasını açın.
2.  `openweathermap.api.key` alanına kendi OpenWeatherMap API anahtarınızı girin.
    *   **Not:** Bu projenin GitHub'a yüklenmiş halinde, bu anahtar güvenlik nedeniyle ortam değişkeni (`${OPENWEATHER_API_KEY:}`) olarak ayarlanmıştır. Yerel geliştirme için doğrudan dosyaya yazabilir veya IDE'nizin ortam değişkeni ayarlama özelliğini kullanabilirsiniz.

#### c) Backend'i Çalıştırma
1.  `WeatherTurkey-api` klasörünün ana dizininde bir terminal açın.
2.  Aşağıdaki Maven komutunu çalıştırın:
    ```bash
    mvn spring-boot:run
    ```
Backend sunucusu `http://localhost:8080` adresinde başlayacaktır.

---

### **2. Frontend (UI) Kurulumu**

#### a) Bağımlılıkları Yükleme
1.  `WeatherTurkey-frontend` klasörünün ana dizininde bir terminal açın.
2.  Gerekli tüm kütüphaneleri yüklemek için aşağıdaki komutu çalıştırın:
    ```bash
    npm install
    ```

#### b) Frontend'i Çalıştırma
1.  Aynı terminalde, geliştirme sunucusunu başlatmak için aşağıdaki komutu çalıştırın:
    ```bash
    npm run dev
    ```
Frontend uygulaması `http://localhost:5173` adresinde başlayacak ve tarayıcınızda otomatik olarak açılacaktır. Artık uygulamayı kullanmaya hazırsınız!

---
*Bu proje, staj kapsamında eğitim amaçlı geliştirilmiştir.*
