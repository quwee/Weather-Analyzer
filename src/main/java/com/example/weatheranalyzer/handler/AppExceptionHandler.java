package com.example.weatheranalyzer.handler;

import com.example.weatheranalyzer.error.ApiError;
import com.example.weatheranalyzer.exception.DateRangeParseException;
import com.example.weatheranalyzer.exception.NoWeatherDataAvailableException;
import com.example.weatheranalyzer.exception.WeatherDataNotFoundException;
import com.example.weatheranalyzer.exception.WeatherParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String message = ex.getLocalizedMessage();
        log.info(message);
        return ResponseEntity.badRequest().body(new ApiError(message));
    }

    @ExceptionHandler(DateRangeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleDateRangeParseException(DateRangeParseException ex) {
            List<String> errors = new ArrayList<>();
            ex.getFields().forEach(field -> {
                String message = "Field '" + field + "' : Incorrect date format, expected: dd-MM-yyyy";
                errors.add(message);
                log.info(message);
            });
            return new ApiError(errors);
    }

    @ExceptionHandler(WeatherParseException.class)
    public void handleWeatherParseException(WeatherParseException ex) {
        log.info(ex.getMessage());
    }

    @ExceptionHandler(NoWeatherDataAvailableException.class)
    public String handleNoWeatherDataAvailable(NoWeatherDataAvailableException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(WeatherDataNotFoundException.class)
    public String handleWeatherDataNotFound(WeatherDataNotFoundException ex) {
        return ex.getMessage();
    }
}
