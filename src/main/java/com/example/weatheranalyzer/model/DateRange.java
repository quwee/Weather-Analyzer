package com.example.weatheranalyzer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = DateRangeDeserializer.class)
public class DateRange {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate from;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate to;
}
