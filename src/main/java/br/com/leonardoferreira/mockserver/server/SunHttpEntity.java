package br.com.leonardoferreira.mockserver.server;

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
class SunHttpEntity implements HttpEntity {

    private final HttpExchange exchange;

    @Override
    public Request getRequest() {
        final Url url = Url.from(exchange.getRequestURI().toString());

        return Request.builder()
                .method(HttpMethod.valueOf(exchange.getRequestMethod()))
                .urn(url.getUrn())
                .queryParams(url.getQueryParams())
                .headers(Header.from(exchange.getRequestHeaders()))
                .build();
    }

    @Override
    public void dispatchResponse(final Response response) {
        addResponseHeader(Header.of("Content-Type", "application/json"));

        if (response.getHeaders() != null) {
            response.getHeaders()
                    .forEach(this::addResponseHeader);
        }

        writeResponseBody(response);

        exchange.close();
    }

    private void addResponseHeader(final Header header) {
        final Headers headers = exchange.getResponseHeaders();
        for (final String headerValue : header.getValues()) {
            headers.add(header.getKey(), headerValue);
        }
    }

    @SneakyThrows
    private void writeResponseBody(final Response response) {
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

}
