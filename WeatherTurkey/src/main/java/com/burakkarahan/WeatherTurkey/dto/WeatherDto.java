package com.burakkarahan.WeatherTurkey.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherDto {

    private String cityName;
    private Double temperature;
    private String description;
    private String icon;

}
