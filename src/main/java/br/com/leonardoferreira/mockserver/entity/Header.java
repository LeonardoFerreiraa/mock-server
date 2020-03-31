package br.com.leonardoferreira.mockserver.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import br.com.leonardoferreira.mockserver.util.StringUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Header {

    private final String key;

    private final List<String> values;

    public static Header of(final String key, final String... values) {
        return new Header(key, Arrays.asList(values));
    }

    public static List<Header> from(final Map<String, List<String>> headers) {
        return headers.entrySet()
                .stream()
                .map(entry -> new Header(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Header header = (Header) o;

        return StringUtils.equalsIgnoreCase(key, header.key) &&
                Objects.equals(values, header.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, values);
    }

}
