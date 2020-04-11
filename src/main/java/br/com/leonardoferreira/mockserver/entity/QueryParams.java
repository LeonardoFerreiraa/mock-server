package br.com.leonardoferreira.mockserver.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.leonardoferreira.mockserver.util.DelegateMap;
import br.com.leonardoferreira.mockserver.util.Pair;
import br.com.leonardoferreira.mockserver.util.StringUtils;

public final class QueryParams extends DelegateMap<String, QueryParam> {

    private QueryParams(final Map<String, QueryParam> delegate) {
        super(delegate);
    }

    public static QueryParams from(final String query) {
        if (query == null) {
            return new QueryParams(null);
        }

        final Map<String, QueryParam> delegate = Arrays.stream(query.split("&"))
                .map(queryParam -> {
                    final Pair<String, String> pair = StringUtils.toPair(queryParam, "=");
                    return QueryParam.of(pair.getFirst(), pair.getSecond());
                })
                .collect(
                        Collectors.toMap(
                                QueryParam::getKey,
                                Function.identity()
                        )
                );

        return new QueryParams(delegate);
    }

    @Override
    protected String transformKey(final Object key) {
        return key == null ? null : key.toString();
    }
}
