package com.burakkarahan.WeatherTurkey.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getWeatherForCity(String city) {
        // OpenWeatherMap API'si Türkçe karakterleri bazen sevmez, bu yüzden &q=Ankara,TR gibi ülke koduyla aratmak daha garantilidir.
        String url = String.format("%s?q=%s,TR&appid=%s&units=metric&lang=tr", apiUrl, city, apiKey);
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            return "{\"error\": \"Veri alınamadı: " + city + "\"}";
        }
    }
}
