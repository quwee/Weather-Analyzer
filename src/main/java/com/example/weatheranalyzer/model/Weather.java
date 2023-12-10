package com.example.weatheranalyzer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "weather")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = WeatherDeserializer.class)
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "created_at")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdAt;

    @Column(name = "temp_c")
    private Double tempC;

    @Column(name = "wind_mph")
    private Double windMph;

    @Column(name = "pressure_mb")
    private Double pressureMb;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "weather_text")
    private String weatherText;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;
}
