package com.burakkarahan.WeatherTurkey.service;

import com.burakkarahan.WeatherTurkey.dto.LoginDto;
import com.burakkarahan.WeatherTurkey.dto.RegisterDto;
import com.burakkarahan.WeatherTurkey.entity.City;
import com.burakkarahan.WeatherTurkey.entity.User;
import com.burakkarahan.WeatherTurkey.exception.BadRequestException;
import com.burakkarahan.WeatherTurkey.exception.ResourceNotFoundException;
import com.burakkarahan.WeatherTurkey.repository.CityRepository;
import com.burakkarahan.WeatherTurkey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BadRequestException("Hata: Bu kullanıcı adı zaten alınmış!");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BadRequestException("Hata: Bu e-posta adresi zaten kullanımda!");
        }

        City userCity = cityRepository.findByName(registerDto.getCityName())
                .orElseThrow(() -> new ResourceNotFoundException("Hata: Geçersiz şehir seçimi: " + registerDto.getCityName()));

        User newUser = User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .city(userCity)
                .build();

        userRepository.save(newUser);

        return "Kullanıcı başarıyla kaydedildi!";
    }

    public String login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        var user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new IllegalStateException("Kimlik doğrulama sonrası kullanıcı bulunamadı."));

        return jwtService.generateToken(user.getUsername());
    }
}