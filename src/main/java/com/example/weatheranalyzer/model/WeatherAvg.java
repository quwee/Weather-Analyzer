package com.example.weatheranalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherAvg {
    private Double tempC;
    private Double windMph;
    private Double pressureMb;
    private Integer humidity;
    private String weatherText;
    private String country;
    private String city;
    private DateRange dateRange;
}
