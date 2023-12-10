package com.example.weatheranalyzer.model;

import com.example.weatheranalyzer.exception.DateRangeParseException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DateRangeDeserializer extends StdDeserializer<DateRange> {

    public DateRangeDeserializer() {
        this(null);
    }

    protected DateRangeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DateRange deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<String> errorFields = new ArrayList<>(2);
        boolean hasExceptions = false;
        LocalDate from = null;
        LocalDate to = null;

        try {
            from = LocalDate.parse(jsonNode.get("from").textValue(), formatter);
        } catch(RuntimeException e) {
            errorFields.add("from");
            hasExceptions = true;
        }
        try {
            to = LocalDate.parse(jsonNode.get("to").textValue(), formatter);
        } catch(RuntimeException e) {
            errorFields.add("to");
            hasExceptions = true;
        }
        if(hasExceptions) {
            throw new DateRangeParseException(errorFields);
        }
        if(from.isAfter(to)) {
            LocalDate temp = from;
            from = to;
            to = temp;
        }
        return new DateRange(from, to);

    }
}
