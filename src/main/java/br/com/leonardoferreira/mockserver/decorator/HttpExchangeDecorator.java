package br.com.leonardoferreira.mockserver.decorator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import br.com.leonardoferreira.mockserver.entity.Header;
import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.entity.Url;
import br.com.leonardoferreira.mockserver.util.Json;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor(staticName = "from")
public class HttpExchangeDecorator {

    private final HttpExchange exchange;

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

        if (response.getHeaders() != null) {
            response.getHeaders()
                    .forEach(this::addHeader);
        }

        setBody(response);
    }

    private void addHeader(final Header header) {
        final Headers headers = exchange.getResponseHeaders();
        for (final String headerValue : header.getValues()) {
            headers.add(header.getKey(), headerValue);
        }
    }

    private void setBody(final Response response) throws IOException {
        final byte[] responseBytes = Optional.ofNullable(response.getBody())
                .map(Json::toJson)
                .orElse(null);

        if (responseBytes == null) {
            exchange.sendResponseHeaders(response.getStatus(), 0);
        } else {
            exchange.sendResponseHeaders(response.getStatus(), responseBytes.length);
            try (final OutputStream responseBody = exchange.getResponseBody()) {
                responseBody.write(responseBytes);
            }
        }
    }

    public void close() {
        exchange.close();
    }

}
