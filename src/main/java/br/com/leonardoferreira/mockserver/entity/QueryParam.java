package br.com.leonardoferreira.mockserver.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.leonardoferreira.mockserver.util.Pair;
import br.com.leonardoferreira.mockserver.util.StringUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class QueryParam {

    private final String key;

    private final String value;

    public static List<QueryParam> from(final String query) {
        return Optional.ofNullable(query)
                .map(it -> it.split("&"))
                .stream()
                .flatMap(Arrays::stream)
                .map(queryParam -> {
                    final Pair<String, String> pair = StringUtils.toPair(queryParam, "=");
                    return QueryParam.of(pair.getFirst(), pair.getSecond());
                })
                .collect(Collectors.toList());
    }

}
