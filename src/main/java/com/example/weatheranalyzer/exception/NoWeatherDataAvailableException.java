package com.example.weatheranalyzer.exception;

public class NoWeatherDataAvailableException extends RuntimeException {
    public NoWeatherDataAvailableException(String message) {
        super(message);
    }
}
