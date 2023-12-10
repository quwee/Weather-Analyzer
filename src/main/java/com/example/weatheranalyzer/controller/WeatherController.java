package com.example.weatheranalyzer.controller;

import com.example.weatheranalyzer.model.DateRange;
import com.example.weatheranalyzer.model.Weather;
import com.example.weatheranalyzer.model.WeatherAvg;
import com.example.weatheranalyzer.service.WeatherAverageService;
import com.example.weatheranalyzer.service.WeatherRetrieveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherRetrieveService weatherRetrieveService;
    private final WeatherAverageService weatherAverageService;

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public Weather getCurrentWeather() {
        return weatherRetrieveService.getLastWeatherData();
    }

    @GetMapping("/average")
    public WeatherAvg getAverageWeather(@RequestBody DateRange dateRange) {
        return weatherAverageService.getAverage(dateRange);
    }
}
