# WeatherTurkey API - İnteraktif Hava Durumu Portalı (Backend)

Bu proje, kullanıcılara anlık ve kişiselleştirilmiş hava durumu verileri sunan dinamik bir web portalının **backend (API)** servisidir. Spring Boot, Spring Security ve PostgreSQL kullanılarak geliştirilmiştir.

Frontend projesine [buradan](https://github.com/SENIN_KULLANICI_ADIN/weather-turkey-frontend) ulaşabilirsiniz. *(Bu linki kendi frontend reponun linki ile değiştir)*

## ✨ Temel Özellikler

*   **Çift Taraflı Kullanıcı Deneyimi:**
    *   **Misafir Kullanıcılar:** Kimlik doğrulaması olmadan, herkese açık endpoint'ler aracılığıyla Türkiye'deki tüm şehirlerin hava durumu verilerine erişebilirler.
    *   **Kayıtlı Kullanıcılar:** Güvenli bir şekilde kaydolup giriş yapabilir ve kendi konumlarına özel, kişiselleştirilmiş bir hava durumu paneli (dashboard) görüntüleyebilirler.
*   **Modern Güvenlik Altyapısı:**
    *   **JWT (JSON Web Token):** "Stateless" kimlik doğrulama için endüstri standardı olan JWT kullanılmıştır.
    *   **Spring Security:** Rol tabanlı yetkilendirme, şifre hash'leme (BCrypt) ve güvenli endpoint yönetimi için sağlam bir altyapı sunar.
*   **Coğrafi Hiyerarşi:** Kullanıcılar kaydolurken seçtikleri şehre göre, ait oldukları coğrafi bölge otomatik olarak saptanır.
*   **Merkezi Hata Yönetimi:** `@ControllerAdvice` ile uygulama genelindeki tüm hatalar yakalanır ve frontend'e standart, anlaşılır bir formatta hata mesajları döndürülür.
*   **Veri Yükleme (Data Seeding):** Uygulama ilk kez çalıştığında, Türkiye'nin 7 coğrafi bölgesi ve 81 ili otomatik olarak veritabanına yüklenir.

## 🛠️ Kullanılan Teknolojiler

*   **Java 17**
*   **Spring Boot 3+**
    *   Spring Web (RESTful API)
    *   Spring Security (Kimlik Doğrulama & Yetkilendirme)
    *   Spring Data JPA (Veritabanı İşlemleri)
*   **PostgreSQL:** İlişkisel veritabanı
*   **Docker:** Veritabanını container içinde çalıştırmak için
*   **Lombok:** Kod tekrarını azaltmak için
*   **Maven:** Bağımlılık yönetimi
*   **jjwt:** JSON Web Token işlemleri için

## 🚀 Kurulum ve Çalıştırma

#### Ön Koşullar
*   Java JDK 17+
*   Apache Maven
*   Docker Desktop

#### 1. Veritabanını Başlatma
Projenin bir PostgreSQL veritabanına ihtiyacı vardır. Aşağıdaki Docker komutu ile hızlıca bir veritabanı ayağa kaldırabilirsiniz:
```bash
docker run --name weather-app-postgres -e POSTGRES_USER=burak -e POSTGRES_PASSWORD=12345 -e POSTGRES_DB=weatherdb -p 5434:5432 -d postgres:15
```

#### 2. Ortam Değişkenlerini Ayarlama
Bu proje, hassas verileri (API Anahtarı vb.) ortam değişkenleri aracılığıyla yönetir. Uygulamayı çalıştırmadan önce aşağıdaki değişkenleri tanımlamanız gerekmektedir:

*   `OPENWEATHER_API_KEY`: OpenWeatherMap'ten aldığınız API anahtarı.

IntelliJ IDEA gibi bir IDE kullanıyorsanız, bu değişkenleri "Run/Debug Configurations" bölümünden kolayca ekleyebilirsiniz.

#### 3. Uygulamayı Başlatma
Projeyi klonladıktan sonra, ana dizinde aşağıdaki Maven komutunu çalıştırın:
```bash
mvn spring-boot:run
```
Uygulama varsayılan olarak `http://localhost:8080` adresinde başlayacaktır.

## 📄 API Endpoint'leri

### Public Endpoints (Herkese Açık)
*   `GET /api/public/cities`: Tüm şehirlerin listesini döndürür.
*   `GET /api/public/weather?city={cityName}`: Belirtilen şehrin hava durumunu döndürür.

### Authentication Endpoints (Kimlik Doğrulama)
*   `POST /api/auth/register`: Yeni bir kullanıcı kaydı oluşturur.
*   `POST /api/auth/login`: Kullanıcı girişi yapar ve bir JWT döndürür.

### Secure Endpoints (Kimlik Doğrulama Gerekli)
*   `GET /api/dashboard`: Giriş yapmış kullanıcının kişiselleştirilmiş panel verilerini döndürür.
*   `PUT /api/user/city`: Giriş yapmış kullanıcının kayıtlı olduğu şehri günceller.

---
*Bu proje, staj kapsamında eğitim amaçlı geliştirilmiştir.*
