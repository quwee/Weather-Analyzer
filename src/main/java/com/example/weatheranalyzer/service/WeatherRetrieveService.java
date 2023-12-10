package com.example.weatheranalyzer.service;

import com.example.weatheranalyzer.exception.NoWeatherDataAvailableException;
import com.example.weatheranalyzer.model.Weather;
import com.example.weatheranalyzer.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherRetrieveService {

    private final WeatherRepository weatherRepository;

    public Weather getLastWeatherData() {
        Weather weather = weatherRepository.findFirstByOrderByIdDesc();
        if(weather == null) {
            String message = "No weather data available.";
            log.info(message + " Table 'weather' is empty.");
            throw new NoWeatherDataAvailableException(message);
        }
        return weather;
    }
}
