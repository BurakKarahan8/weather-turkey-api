package com.burakkarahan.WeatherTurkey.controller;

import com.burakkarahan.WeatherTurkey.dto.UpdateCityDto;
import com.burakkarahan.WeatherTurkey.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/city")
    public ResponseEntity<String> updateUserCity(@RequestBody UpdateCityDto updateCityDto) {
        try {
            userService.updateUserCity(updateCityDto.getNewCityName());
            return ResponseEntity.ok("Şehir başarıyla güncellendi.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
