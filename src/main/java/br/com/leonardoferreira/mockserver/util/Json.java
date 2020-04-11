package br.com.leonardoferreira.mockserver.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public final class Json {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
    }

    private Json() {
    }

    @SneakyThrows
    public static byte[] toJson(final Object object) {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    @SneakyThrows
    public static <T> T read(final byte[] json, final Class<T> clazz) {
        return OBJECT_MAPPER.readValue(json, clazz);
    }
}
