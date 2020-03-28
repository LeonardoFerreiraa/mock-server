package br.com.leonardoferreira.mockserver.entity;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class Header {

    private final String key;

    private final List<String> values;

    public static Header of(final String key, final String... values) {
        return new Header(key, Arrays.asList(values));
    }

}
