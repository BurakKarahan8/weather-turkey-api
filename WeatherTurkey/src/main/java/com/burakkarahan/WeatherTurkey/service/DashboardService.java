package com.burakkarahan.WeatherTurkey.service;

import com.burakkarahan.WeatherTurkey.dto.DashboardDto;
import com.burakkarahan.WeatherTurkey.dto.WeatherDto;
import com.burakkarahan.WeatherTurkey.entity.City;
import com.burakkarahan.WeatherTurkey.entity.Region;
import com.burakkarahan.WeatherTurkey.entity.User;
import com.burakkarahan.WeatherTurkey.repository.CityRepository;
import com.burakkarahan.WeatherTurkey.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final WeatherService weatherService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional(readOnly = true)
    public DashboardDto getDashboardData() {
        try {
            // Adım 1: Güvenlik context'inden mevcut kullanıcının kimliğini al
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            logger.info("ADIM 1: Dashboard verisi '{}' kullanıcısı için çekiliyor.", username);

            // Adım 2: Kullanıcıyı ve ilişkili verilerini veritabanından bul
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("HATA: Kullanıcı veritabanında bulunamadı: " + username));
            logger.info("ADIM 2: Kullanıcı '{}' veritabanından bulundu.", user.getUsername());

            // Adım 3: Kullanıcının şehir bilgisini kontrol et
            City userCity = user.getCity();
            if (userCity == null) {
                logger.error("HATA: '{}' kullanıcısının şehir (city) alanı NULL! Veritabanı ilişkisi kayıt sırasında doğru kurulmamış.", username);
                throw new IllegalStateException("Kullanıcının şehir bilgisi eksik.");
            }
            logger.info("ADIM 3: Kullanıcının şehri: '{}'", userCity.getName());

            // Adım 4: Şehrin bölge bilgisini kontrol et
            Region userRegion = userCity.getRegion();
            if (userRegion == null) {
                logger.error("HATA: '{}' şehrinin bölge (region) alanı NULL! Veri yükleme (seeding) veya ilişki tanımında sorun olabilir.", userCity.getName());
                throw new IllegalStateException("Sistemsel hata: Şehir-bölge ilişkisi kurulamamış.");
            }
            logger.info("ADIM 4: Şehrin bölgesi: '{}'", userRegion.getName());

            // Adım 5: Hava durumu verilerini çekmeye başla
            logger.info("ADIM 5: Hava durumu verileri OpenWeatherMap'ten çekiliyor...");
            WeatherDto userCityWeather = fetchAndParseWeather(userCity.getName());

            List<WeatherDto> regionCitiesWeather = cityRepository.findAllByRegion(userRegion).stream()
                    .filter(city -> !city.getId().equals(userCity.getId()))
                    .map(city -> fetchAndParseWeather(city.getName()))
                    .collect(Collectors.toList());

            logger.info("ADIM 6: '{}' kullanıcısı için dashboard verisi başarıyla oluşturuldu.", username);

            return DashboardDto.builder()
                    .userCityWeather(userCityWeather)
                    .regionCitiesWeather(regionCitiesWeather)
                    .build();

        } catch (Exception e) {
            // Metodun herhangi bir yerinde beklenmedik bir hata olursa bunu logla
            logger.error("Dashboard verisi oluşturulurken BEKLENMEDİK BİR HATA oluştu!", e);
            // Hatayı tekrar fırlat. Bu, Spring'in işlemi geri almasını ve /error'a yönlendirmesini sağlar.
            throw e;
        }
    }

    private WeatherDto fetchAndParseWeather(String cityName) {
        try {
            String jsonResponse = weatherService.getWeatherForCity(cityName);
            JsonNode root = objectMapper.readTree(jsonResponse);

            if (root.has("cod") && root.get("cod").asInt() != 200) {
                logger.warn("OpenWeatherMap'ten '{}' şehri için veri alınamadı: {}", cityName, root.path("message").asText());
                return WeatherDto.builder().cityName(cityName).temperature(0.0).description("Veri yok").icon("01d").build();
            }

            return WeatherDto.builder()
                    .cityName(root.path("name").asText())
                    .temperature(root.path("main").path("temp").asDouble())
                    .description(root.path("weather").get(0).path("description").asText())
                    .icon(root.path("weather").get(0).path("icon").asText())
                    .build();
        } catch (IOException e) {
            logger.error("'{}' şehri için gelen JSON parse edilirken hata oluştu: {}", cityName, e.getMessage());
            return WeatherDto.builder().cityName(cityName).temperature(0.0).description("Servis hatası").icon("01d").build();
        }
    }
}