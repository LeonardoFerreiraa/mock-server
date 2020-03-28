package br.com.leonardoferreira.mockserver.entity;

import java.util.List;

import br.com.leonardoferreira.mockserver.entity.QueryParam;
import br.com.leonardoferreira.mockserver.entity.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlTest {

    @Test
    void shouldParseWithSuccess() {
        final Url url = Url.from("/customer?name=mario&lastName=armario");
        Assertions.assertEquals("/customer", url.getUrn());

        final List<QueryParam> queryParams = url.getQueryParams();
        Assertions.assertEquals(2, queryParams.size());

        final QueryParam first = queryParams.get(0);
        Assertions.assertEquals("name", first.getKey());
        Assertions.assertEquals("mario", first.getValue());

        final QueryParam second = queryParams.get(1);
        Assertions.assertEquals("lastName", second.getKey());
        Assertions.assertEquals("armario", second.getValue());
    }

}