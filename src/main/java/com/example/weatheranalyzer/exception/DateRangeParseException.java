package com.example.weatheranalyzer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DateRangeParseException extends RuntimeException {
    private List<String> fields;
}
