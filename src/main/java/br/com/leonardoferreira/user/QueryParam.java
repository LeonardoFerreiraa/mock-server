package br.com.leonardoferreira.user;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .map(qp -> {
                    final String[] split = qp.split("=", 2);
                    if (split.length == 2) {
                        return QueryParam.of(split[0], split[1]);
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
