package com.example.weatheranalyzer.controller;

import com.example.weatheranalyzer.error.ApiError;
import com.example.weatheranalyzer.exception.DateRangeParseException;
import com.example.weatheranalyzer.model.DateRange;
import com.example.weatheranalyzer.model.Weather;
import com.example.weatheranalyzer.model.WeatherAvg;
import com.example.weatheranalyzer.service.WeatherAverageService;
import com.example.weatheranalyzer.service.WeatherRetrieveService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @MockBean
    WeatherAverageService weatherAverageService;

    @MockBean
    WeatherRetrieveService weatherRetrieveService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    void givenWeather_whenGetCurrentWeather_thenValidWeather() throws Exception {
        Weather weather = Weather.builder()
                .tempC(1.0)
                .windMph(1.0)
                .pressureMb(1.0)
                .humidity(1)
                .weatherText("Snowy")
                .country("Belarus")
                .city("Minsk")
                .build();

        when(weatherRetrieveService.getLastWeatherData()).thenReturn(weather);

        mockMvc.perform(get("/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(weather));
    }


    @Test
    void givenValidDateRange_whenGetAverageWeather_thenValidWeatherAvg() throws Exception {
        DateRange dateRange = new DateRange(
                LocalDate.of(2023, 11, 21),
                LocalDate.of(2023, 12, 11));
        String dateRangeJson = mapper.writeValueAsString(dateRange);
        WeatherAvg weatherAvg = WeatherAvg.builder()
                .tempC(1.0)
                .windMph(1.0)
                .pressureMb(1.0)
                .humidity(1)
                .weatherText("Snowy")
                .country("Belarus")
                .city("Minsk")
                .dateRange(dateRange)
                .build();

        when(weatherAverageService.getAverage(dateRange)).thenReturn(weatherAvg);

        mockMvc.perform(get("/average")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dateRangeJson))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(weatherAvg,
                        mapper.readValue(result.getResponse().getContentAsString(), WeatherAvg.class)));
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    void givenInvalidDateRangeFormat_whenGetAverageWeather_thenHandle(String invalidJson) throws Exception {
        mockMvc.perform(get("/average")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof DateRangeParseException))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                        .contains("Incorrect date format, expected: dd-MM-yyyy")));
    }

    static List<String> invalidData() {
        return List.of(
                "{\"form\": 1, \"to\": 2}",
                "{\"form\": \"2023-10-13\", \"to\": \"22-11-2023\"}",
                "{\"form\": null, \"to\": null}"
        );
    }
}