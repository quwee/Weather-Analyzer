package com.example.weatheranalyzer.service;

import com.example.weatheranalyzer.model.DateRange;
import com.example.weatheranalyzer.model.Weather;
import com.example.weatheranalyzer.model.WeatherAvg;
import com.example.weatheranalyzer.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(WeatherAverageService.class)
class WeatherAverageServiceTest {

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    WeatherAverageService weatherAverageService;

    @Test
    void givenSomePersistedWeather_whenGetAverage_thenValidWeatherAvg() {
        Weather weather1 = Weather.builder()
                .createdAt(LocalDate.now())
                .tempC(1.0)
                .windMph(1.0)
                .pressureMb(1.0)
                .humidity(1)
                .weatherText("Snowy")
                .country("Belarus")
                .city("Minsk")
                .build();

        Weather weather2 = Weather.builder()
                .createdAt(LocalDate.now())
                .tempC(2.0)
                .windMph(2.0)
                .pressureMb(2.0)
                .humidity(2)
                .weatherText("Overcast")
                .country("Belarus")
                .city("Minsk")
                .build();

        Weather weather3 = Weather.builder()
                .createdAt(LocalDate.now())
                .tempC(3.0)
                .windMph(3.0)
                .pressureMb(3.0)
                .humidity(3)
                .weatherText("Snowy")
                .country("Belarus")
                .city("Minsk")
                .build();

        DateRange dateRange = new DateRange(LocalDate.now(), LocalDate.now());

        weatherRepository.save(weather1);
        weatherRepository.save(weather2);
        weatherRepository.save(weather3);

        WeatherAvg expected = WeatherAvg.builder()
                .tempC(2.0)
                .windMph(2.0)
                .pressureMb(2.0)
                .humidity(2)
                .weatherText("Snowy")
                .country("Belarus")
                .city("Minsk")
                .dateRange(dateRange)
                .build();

        WeatherAvg actual = weatherAverageService.getAverage(dateRange);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}