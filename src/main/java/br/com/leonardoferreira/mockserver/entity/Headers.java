package br.com.leonardoferreira.mockserver.entity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.leonardoferreira.mockserver.util.DelegateMap;

public class Headers extends DelegateMap<String, Header> {

    public Headers(final Map<String, Header> delegate) {
        super(delegate);
    }

    public static Headers from(final Map<String, List<String>> headers) {
        final Map<String, Header> delegate = headers.entrySet()
                .stream()
                .map(entry -> new Header(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(
                        h -> h.getKey().toLowerCase(),
                        Function.identity()
                ));

        return new Headers(delegate);
    }

    protected String transformKey(final Object key) {
        return key == null ? null : key.toString().toLowerCase();
    }

}
