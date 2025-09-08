package com.burakkarahan.WeatherTurkey.controller;

import com.burakkarahan.WeatherTurkey.dto.DashboardDto;
import com.burakkarahan.WeatherTurkey.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {


    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDto> getDashboardDataForUser() {
        DashboardDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
}
