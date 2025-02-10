package com.effortstone.backend.domain.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<List<Integer>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        if (attribute == null) return null;
        try {
            // List<Integer> -> JSON 문자열 ("[0,1,2]")
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            // JSON 문자열("[0,1,2]") -> List<Integer>
            return objectMapper.readValue(dbData, new TypeReference<List<Integer>>() {});
        } catch (IOException e) {
            throw new RuntimeException("JSON 읽기 실패", e);
        }
    }
}