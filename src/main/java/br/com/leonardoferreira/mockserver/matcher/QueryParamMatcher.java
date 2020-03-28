package br.com.leonardoferreira.mockserver.matcher;

import java.util.List;

import br.com.leonardoferreira.mockserver.entity.QueryParam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class QueryParamMatcher {

    private final List<QueryParam> queryParams;

    public boolean match(final List<QueryParam> requestedQueryParams) {
        if (queryParams == null) {
            return true;
        }

        return queryParams.stream()
                .allMatch(queryParam ->
                        requestedQueryParams.stream()
                                .anyMatch(queryParam::equals)
                );
    }

}
