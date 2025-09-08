package com.burakkarahan.WeatherTurkey.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardDto {

    // Kullanıcının kendi şehrinin detaylı hava durumu
    private WeatherDto userCityWeather;

    // Kullanıcının bulunduğu bölgedeki diğer şehirlerin hava durumu listesi
    private List<WeatherDto> regionCitiesWeather;

    // Not: Gelecekte buraya Türkiye'nin 7 bölgesinin genel durumu gibi
    // yeni alanlar da eklenebilir.
}