package com.burakkarahan.WeatherTurkey.controller;

import com.burakkarahan.WeatherTurkey.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/weather")
public class PublicWeatherController {

    // Final olarak tanımlanan service, constructor'da enjekte edilecek.
    private final WeatherService weatherService;

    // Spring, WeatherService'i otomatik olarak bulup bu constructor'a parametre olarak geçirecek (Dependency Injection).
    public PublicWeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Şehir adına göre hava durumu verisini getiren herkese açık endpoint.
     * Örnek istek: http://localhost:8080/api/public/weather?city=Izmir
     * @param city Hava durumu bilgisi istenen şehir adı.
     * @return OpenWeatherMap'ten gelen hava durumu verisi (JSON formatında String).
     */
    @GetMapping
    public String getPublicWeather(@RequestParam String city) {
        // Gelen şehir parametresini doğrudan servis katmanına iletiyoruz.
        return weatherService.getWeatherForCity(city);
    }
}
