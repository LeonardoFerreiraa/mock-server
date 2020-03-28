package br.com.leonardoferreira.mockserver.decorator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import br.com.leonardoferreira.mockserver.entity.Header;
import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.entity.Url;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor(staticName = "from")
public class HttpExchangeDecorator implements AutoCloseable {

    private final HttpExchange exchange;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Request toRequest() {
        return Request.builder()
                .method(HttpMethod.valueOf(exchange.getRequestMethod()))
                .url(Url.from(exchange.getRequestURI().toString()))
                .headers(Header.from(exchange.getRequestHeaders()))
                .build();
    }

    @SneakyThrows
    public void respond(final Response response) {
        addHeader(Header.of("Content-Type", "application/json"));

        Optional.ofNullable(response.getHeaders())
                .stream()
                .flatMap(List::stream)
                .forEach(this::addHeader);

        setBody(response.getStatus(), objectMapper.writeValueAsBytes(response.getBody()));
    }

    private void addHeader(final Header header) {
        final Headers headers = exchange.getResponseHeaders();
        for (final String headerValue : header.getValues()) {
            headers.add(header.getKey(), headerValue);
        }
    }

    private void setBody(final int status, final byte[] responseBytes) throws IOException {
        exchange.sendResponseHeaders(status, responseBytes.length);
        try (final OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write(responseBytes);
        }
    }

    @Override
    public void close() throws Exception {
        exchange.close();
    }
}
