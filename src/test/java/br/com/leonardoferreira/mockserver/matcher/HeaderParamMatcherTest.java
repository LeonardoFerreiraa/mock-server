package br.com.leonardoferreira.mockserver.matcher;

import java.util.Arrays;
import java.util.Collections;

import br.com.leonardoferreira.mockserver.entity.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeaderParamMatcherTest {

    @Test
    void shouldMatchWithSingleHeader() {
        final HeaderMatcher queryParamMatcher = HeaderMatcher.from(Collections.singletonList(Header.of("x-name", "mario")));

        final boolean match = queryParamMatcher.match(Collections.singletonList(Header.of("x-name", "mario")));

        Assertions.assertTrue(match);
    }

    @Test
    void shouldMatchWhenRequestedHasMoreData() {
        final HeaderMatcher queryParamMatcher = HeaderMatcher.from(Collections.singletonList(Header.of("x-name", "mario")));

        final boolean match = queryParamMatcher.match(Arrays.asList(Header.of("x-name", "mario"), Header.of("x-lastName", "armario")));

        Assertions.assertTrue(match);
    }

    @Test
    void shouldNotMatch() {
        final HeaderMatcher queryParamMatcher = HeaderMatcher.from(Collections.singletonList(Header.of("x-name", "oiram")));

        final boolean match = queryParamMatcher.match(Collections.singletonList(Header.of("x-name", "mario")));

        Assertions.assertFalse(match);
    }

    @Test
    void shouldNotMatchWhenLessData() {
        final HeaderMatcher queryParamMatcher = HeaderMatcher.from(Arrays.asList(Header.of("x-name", "mario"), Header.of("x-lastName", "armario")));

        final boolean match = queryParamMatcher.match(Collections.singletonList(Header.of("x-name", "mario")));

        Assertions.assertFalse(match);
    }

}