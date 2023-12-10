package com.example.weatheranalyzer.service;

import com.example.weatheranalyzer.model.Weather;
import com.example.weatheranalyzer.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(WeatherRetrieveService.class)
class WeatherRetrieveServiceTest {

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    WeatherRetrieveService weatherRetrieveService;

    @Test
    void givenCouplePersistedWeather_whenFindFirstByOrderByIdDesc_thenValidWeather() {

        Weather weather1 = Weather.builder()
                .createdAt(LocalDate.now())
                .tempC(1.1)
                .windMph(11.1)
                .pressureMb(1000.2)
                .humidity(90)
                .weatherText("Snowy")
                .country("Belarus")
                .city("Minsk")
                .build();

        Weather weather2 = Weather.builder()
                .createdAt(LocalDate.now())
                .tempC(1.1)
                .windMph(11.1)
                .pressureMb(1000.2)
                .humidity(90)
                .weatherText("Snowy")
                .country("Belarus")
                .city("Minsk")
                .build();

        weatherRepository.save(weather1);
        weatherRepository.save(weather2);

        Weather lastSavedWeather = weatherRetrieveService.getLastWeatherData();

        assertEquals(weatherRepository.findById(2L).orElseThrow(), lastSavedWeather);
        assertNotNull(lastSavedWeather);
    }
}



