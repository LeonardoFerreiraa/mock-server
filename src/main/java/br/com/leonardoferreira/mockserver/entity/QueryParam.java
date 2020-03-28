package br.com.leonardoferreira.mockserver.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        if (query == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(query.split("&"))
                .map(queryParam -> {
                    final Pair<String, String> pair = StringUtils.toPair(queryParam, "=");
                    return QueryParam.of(pair.getFirst(), pair.getSecond());
                })
                .collect(Collectors.toList());
    }

}
