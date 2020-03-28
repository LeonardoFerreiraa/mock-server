package br.com.leonardoferreira.mockserver.matcher;

import java.util.Arrays;
import java.util.Collections;

import br.com.leonardoferreira.mockserver.entity.QueryParam;
import br.com.leonardoferreira.mockserver.matcher.QueryParamMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QueryParamMatcherTest {

    @Test
    void shouldMatchWithSingleQueryParam() {
        final QueryParamMatcher queryParamMatcher = QueryParamMatcher.from(Collections.singletonList(QueryParam.of("name", "mario")));

        final boolean match = queryParamMatcher.match(Collections.singletonList(QueryParam.of("name", "mario")));

        Assertions.assertTrue(match);
    }

    @Test
    void shouldMatchWhenRequestedHasMoreData() {
        final QueryParamMatcher queryParamMatcher = QueryParamMatcher.from(Collections.singletonList(QueryParam.of("name", "mario")));

        final boolean match = queryParamMatcher.match(Arrays.asList(QueryParam.of("name", "mario"), QueryParam.of("lastName", "armario")));

        Assertions.assertTrue(match);
    }

    @Test
    void shouldNotMatch() {
        final QueryParamMatcher queryParamMatcher = QueryParamMatcher.from(Collections.singletonList(QueryParam.of("name", "oiram")));

        final boolean match = queryParamMatcher.match(Collections.singletonList(QueryParam.of("name", "mario")));

        Assertions.assertFalse(match);
    }

    @Test
    void shouldNotMatchWhenLessData() {
        final QueryParamMatcher queryParamMatcher = QueryParamMatcher.from(Arrays.asList(QueryParam.of("name", "mario"), QueryParam.of("lastName", "armario")));

        final boolean match = queryParamMatcher.match(Collections.singletonList(QueryParam.of("name", "mario")));

        Assertions.assertFalse(match);
    }

}