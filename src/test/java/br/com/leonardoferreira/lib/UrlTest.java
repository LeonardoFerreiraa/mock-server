package br.com.leonardoferreira.lib;

import org.junit.jupiter.api.Test;

class UrlTest {

    @Test
    void shouldBla() {
        final Url url = Url.from("/customer?name=mario&lastName=arm&ario");
        System.out.println(url);
    }

}