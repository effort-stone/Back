package com.effortstone.backend.domain.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JacksonConfig {
    /**
     * BooleanToIntSerializer는 Jackson의 JsonSerializer를 확장하여
     * Boolean 타입의 값을 직렬화할 때, true는 1로, false 또는 null은 0으로 변환하여 출력합니다.
     */
    public static class BooleanToIntSerializer extends JsonSerializer<Boolean> {
        @Override
        // Boolean 값이 true이면 1, 그렇지 않으면(즉, false 또는 null이면) 0을 출력합니다.
        public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(Boolean.TRUE.equals(value) ? 1 : 0);
        }
    }

    /**
     * Jackson2ObjectMapperBuilderCustomizer 빈을 등록하여,
     * 애플리케이션 전역에서 Jackson의 ObjectMapper에 커스텀 모듈을 추가합니다.
     * 이 모듈은 모든 Boolean 및 boolean 타입에 대해 BooleanToIntSerializer를 적용합니다.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customBooleanSerializer() {
        return builder -> {
            // SimpleModule을 생성하여 커스텀 Serializer를 등록합니다.
            SimpleModule module = new SimpleModule();
            // Boolean 클래스 (객체형)와 기본 boolean 타입 모두에 적용합니다.
            module.addSerializer(Boolean.class, new BooleanToIntSerializer());
            module.addSerializer(boolean.class, new BooleanToIntSerializer());
            // 생성한 모듈을 ObjectMapper에 등록합니다.
            builder.modules(module);
        };
    }
}
