package com.example.weatheranalyzer.service;

import com.example.weatheranalyzer.model.Weather;
import com.example.weatheranalyzer.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class WeatherUpdateService {

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    @Value("${weather-update.base-url}")
    private String baseUrl;

    @Value("${weather-update.location}")
    private String location;

    @Scheduled(fixedDelay = 30_000)
    public void updateWeatherData() {
        String url = String.format("%s&q=%s", baseUrl, location);
        Weather weather = restTemplate.getForObject(url, Weather.class);
        if(weather == null) {
            log.info("Received null object from Weather API");
            return;
        }
        weatherRepository.save(weather);
    }
}
