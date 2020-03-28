package br.com.leonardoferreira.mockserver.entity;

import java.util.List;

import br.com.leonardoferreira.mockserver.util.Pair;
import br.com.leonardoferreira.mockserver.util.StringUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Data
@RequiredArgsConstructor
public class Url {

    private final String urn;

    private final List<QueryParam> queryParams;

    @SneakyThrows
    public static Url from(final String url) {
        final Pair<String, String> pair = StringUtils.toPair(url, "\\?");

        final String urn = pair.getFirst();
        final String queryRaw = pair.getSecond();
        final List<QueryParam> queryParams = QueryParam.from(queryRaw);

        return new Url(urn, queryParams);
    }

}
