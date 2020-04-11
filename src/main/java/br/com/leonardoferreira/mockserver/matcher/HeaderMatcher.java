package br.com.leonardoferreira.mockserver.matcher;

import java.util.Collection;
import java.util.List;

import br.com.leonardoferreira.mockserver.entity.Header;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class HeaderMatcher {

    private final List<Header> headers;

    public boolean match(final Collection<Header> requestedHeaders) {
        if (headers == null) {
            return true;
        }

        return headers.stream()
                .allMatch(header ->
                        requestedHeaders.stream()
                                .anyMatch(header::equals)
                );
    }

}
