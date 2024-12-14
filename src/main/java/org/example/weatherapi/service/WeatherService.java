package org.example.weatherapi.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${root.url}")
    private String rootUrl;

    private final RestTemplate restTemplate;

    @Cacheable(value = "forecast")
    public Map<String, BigDecimal> getWeather(String location) {
        String rawJson = restTemplate.getForObject(rootUrl.formatted(location), String.class);
        JSONObject obj = new JSONObject(rawJson);
        Map<String, BigDecimal> data = new HashMap<>();

        JSONArray jsonArray = obj.getJSONArray("days");
        for (int i = 0; i < obj.getJSONArray("days").length() ; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            data.put(jsonObject.getString("datetime"), jsonObject.getBigDecimal("temp"));
        }

        return data;
    }
}
