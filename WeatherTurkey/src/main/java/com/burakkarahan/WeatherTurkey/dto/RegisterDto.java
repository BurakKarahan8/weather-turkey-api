package com.burakkarahan.WeatherTurkey.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String email;
    private String password;
    private String cityName; // Kullanıcı kaydolurken sadece şehrin adını gönderecek
}
