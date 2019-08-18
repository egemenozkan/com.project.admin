package com.project.client.dao;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.data.model.event.TimeTable;

@Component
public class TimeTableConverter implements Converter<String, TimeTable> {

    private final ObjectMapper objectMapper;

    public TimeTableConverter (ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public TimeTable convert(String source) {
        try {
            return objectMapper.readValue(source, TimeTable.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}