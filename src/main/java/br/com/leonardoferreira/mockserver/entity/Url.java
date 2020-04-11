package br.com.leonardoferreira.mockserver.entity;

import br.com.leonardoferreira.mockserver.util.Pair;
import br.com.leonardoferreira.mockserver.util.StringUtils;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Data
@RequiredArgsConstructor
public class Url {

    private final String fullUrl;

    private final String urn;

    private final QueryParams queryParams;

    @SneakyThrows
    public static Url from(final String url) {
        final Pair<String, String> pair = StringUtils.toPair(url, "\\?");

        final String urn = pair.getFirst();
        final String queryRaw = pair.getSecond();
        final QueryParams queryParams = QueryParams.from(queryRaw);

        return new Url(url, urn, queryParams);
    }

}
