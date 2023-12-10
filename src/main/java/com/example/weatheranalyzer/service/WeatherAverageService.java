package com.example.weatheranalyzer.service;

import com.example.weatheranalyzer.exception.WeatherDataNotFoundException;
import com.example.weatheranalyzer.model.DateRange;
import com.example.weatheranalyzer.model.Weather;
import com.example.weatheranalyzer.model.WeatherAvg;
import com.example.weatheranalyzer.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherAverageService {

    private final WeatherRepository weatherRepository;

    public WeatherAvg getAverage(DateRange dateRange) {
        List<Weather> weatherList = weatherRepository.findAllByCreatedAtBetween(
                dateRange.getFrom(), dateRange.getTo());
        if(weatherList.isEmpty()) {
            String message = String.format("No weather data available between %s and %s.",
                    dateRange.getFrom(), dateRange.getTo());
            log.info(message);
            throw new WeatherDataNotFoundException(message);
        }
        int size = weatherList.size();
        double tempCTotal = 0;
        double windMphTotal = 0;
        double pressureMbTotal = 0;
        int humidityTotal = 0;
        Map<String, Integer> weatherTextMap = new HashMap<>();

        for(Weather weather : weatherList) {
            tempCTotal += weather.getTempC();
            windMphTotal += weather.getWindMph();
            pressureMbTotal += weather.getPressureMb();
            humidityTotal += weather.getHumidity();
            String weatherText = weather.getWeatherText();

            if(weatherTextMap.containsKey(weatherText)) {
                Integer count = weatherTextMap.get(weatherText) + 1;
                weatherTextMap.put(weatherText, count);
            }
            else {
                weatherTextMap.put(weatherText, 1);
            }
        }

        String weatherText = weatherTextMap
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        return WeatherAvg.builder()
                .tempC(Math.round(tempCTotal / size * 100.0) / 100.0)
                .windMph(Math.round(windMphTotal / size * 100.0) / 100.0)
                .pressureMb(Math.round(pressureMbTotal / size * 100.0) / 100.0)
                .humidity(humidityTotal / size)
                .weatherText(weatherText)
                .country(weatherList.get(0).getCountry())
                .city(weatherList.get(0).getCity())
                .dateRange(dateRange)
                .build();
    }
}
