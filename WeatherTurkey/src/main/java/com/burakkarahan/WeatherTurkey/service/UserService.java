package com.burakkarahan.WeatherTurkey.service;

import com.burakkarahan.WeatherTurkey.entity.City;
import com.burakkarahan.WeatherTurkey.entity.User;
import com.burakkarahan.WeatherTurkey.repository.CityRepository;
import com.burakkarahan.WeatherTurkey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
    }

    public void updateUserCity(String newCityName) {
        User currentUser = getCurrentUser();

        City newCity = cityRepository.findByName(newCityName)
                .orElseThrow(() -> new RuntimeException("Yeni şehir bulunamadı!"));

        currentUser.setCity(newCity);
        userRepository.save(currentUser);
    }
}
