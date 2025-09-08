package com.burakkarahan.WeatherTurkey.service;

import com.burakkarahan.WeatherTurkey.dto.CityDto;
import com.burakkarahan.WeatherTurkey.entity.City;
import com.burakkarahan.WeatherTurkey.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<CityDto> getAllCities() {
        // Tüm şehirleri bul ve alfabetik olarak sırala
        List<City> cities = cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        // City entity'lerini CityDto'ya dönüştür
        return cities.stream()
                .map(city -> new CityDto(city.getName()))
                .collect(Collectors.toList());
    }
}