package br.com.leonardoferreira.mockserver.matcher;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import br.com.leonardoferreira.mockserver.entity.QueryParam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QueryParamMatcher {

    private final List<QueryParam> queryParams;

    public static QueryParamMatcher from(final List<QueryParam> queryParams) {
        return new QueryParamMatcher(Optional.ofNullable(queryParams).orElseGet(Collections::emptyList));
    }

    public boolean match(final List<QueryParam> requestedQueryParams) {
        return this.queryParams.stream()
                .allMatch(queryParam ->
                        requestedQueryParams.stream()
                                .anyMatch(queryParam::equals)
                );
    }

}
