package br.com.leonardoferreira.mockserver.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UrlTest {

    @Test
    void shouldParseWithSuccess() {
        final Url url = Url.from("/customer?name=mario&lastName=armario");
        Assertions.assertEquals("/customer", url.getUrn());

        final QueryParams queryParams = url.getQueryParams();
        Assertions.assertEquals(2, queryParams.size());

        final QueryParam first = queryParams.get("name");
        Assertions.assertEquals("name", first.getKey());
        Assertions.assertEquals("mario", first.getValue());

        final QueryParam second = queryParams.get("lastName");
        Assertions.assertEquals("lastName", second.getKey());
        Assertions.assertEquals("armario", second.getValue());
    }

}