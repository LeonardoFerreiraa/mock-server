package br.com.leonardoferreira.mockserver.matcher;

import br.com.leonardoferreira.mockserver.matcher.UrnMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UrnMatcherTest {

    @ParameterizedTest
    @CsvSource({
            "/customer/, customer",
            "/customer/, /customer",
            "/customer/, customer/",
            "/customer/, /customer/",
            "customer, customer",
            "customer, /customer/",
            "customer/, customer",
            "/customer, customer"
    })
    void shouldMatchIgnoringSlashAround(final String url, final String urlRequested) {
        final UrnMatcher from = UrnMatcher.from(url);

        final boolean result = from.match(urlRequested);

        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
            "/customer/{id}, /customer/123",
            "/customer/{id}, /customer/321",
            "/customer/{id}, /customer/abc",
            "/customer/{name}, /customer/mario"
    })
    void shouldMatchWithPathVariable(final String url, final String urlRequested) {
        final UrnMatcher from = UrnMatcher.from(url);

        final boolean result = from.match(urlRequested);

        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
            "/customer/{id}, /customer/123/asd",
            "/customer, /customer/asd",
    })
    void shouldNotMatch(final String url, final String urlRequested) {
        final UrnMatcher from = UrnMatcher.from(url);

        final boolean result = from.match(urlRequested);

        Assertions.assertFalse(result);
    }


}