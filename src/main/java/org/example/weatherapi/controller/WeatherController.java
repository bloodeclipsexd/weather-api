package org.example.weatherapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weatherapi.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/get")
    public ResponseEntity<Map<String, BigDecimal>> getWeather(@RequestParam String location) {
        LocalDateTime start = LocalDateTime.now();
        log.info("Get weather for {}", location);
        Map<String, BigDecimal> weather = weatherService.getWeather(location);
        LocalDateTime finish = LocalDateTime.now();
        log.info("Duration between start and finish - {} ms", Duration.between(start, finish).toMillis());
        return ResponseEntity.ok(weather);
    }
}
