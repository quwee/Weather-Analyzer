package com.example.weatheranalyzer.model;

import com.example.weatheranalyzer.exception.WeatherParseException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class WeatherDeserializer extends StdDeserializer<Weather> {

    public WeatherDeserializer() {
        this(null);
    }

    protected WeatherDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Weather deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        Weather weather;
        try {
            weather = Weather.builder()
                    .createdAt(LocalDate.now())
                    .tempC(jsonNode.get("current").get("temp_c").doubleValue())
                    .windMph(jsonNode.get("current").get("wind_mph").doubleValue())
                    .pressureMb(jsonNode.get("current").get("pressure_mb").doubleValue())
                    .humidity(jsonNode.get("current").get("humidity").intValue())
                    .weatherText(jsonNode.get("current").get("condition").get("text").textValue())
                    .country(jsonNode.get("location").get("country").textValue())
                    .city(jsonNode.get("location").get("name").textValue())
                    .build();
        } catch (RuntimeException e) {
            throw new WeatherParseException(e.getMessage());
        }
        return weather;
    }
}
