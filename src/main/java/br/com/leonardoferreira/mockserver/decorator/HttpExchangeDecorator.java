package br.com.leonardoferreira.mockserver.decorator;

import java.io.IOException;
import java.io.OutputStream;

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
                .build();
    }

    @SneakyThrows
    public void respond(final Response response) {
        addHeader("Content-Type", "application/json");
        setBody(response.getStatus(), objectMapper.writeValueAsBytes(response.getBody()));
    }

    private void addHeader(final String key, final String value) {
        final Headers headers = exchange.getResponseHeaders();
        headers.add(key, value);
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
