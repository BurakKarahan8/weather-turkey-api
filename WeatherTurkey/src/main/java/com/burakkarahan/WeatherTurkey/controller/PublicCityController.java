package com.burakkarahan.WeatherTurkey.controller;

import com.burakkarahan.WeatherTurkey.dto.CityDto;
import com.burakkarahan.WeatherTurkey.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/cities")
@RequiredArgsConstructor
public class PublicCityController {

    private final CityService cityService;

    // GET http://localhost:8080/api/public/cities
    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }
}