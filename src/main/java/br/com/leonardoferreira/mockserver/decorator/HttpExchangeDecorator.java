package br.com.leonardoferreira.mockserver.decorator;

import java.io.IOException;

import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Url;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class HttpExchangeDecorator {

    private final HttpExchange exchange;

    public Request toRequest() {
        return Request.builder()
                .method(HttpMethod.valueOf(exchange.getRequestMethod()))
                .url(Url.from(exchange.getRequestURI().toString()))
                .build();
    }

    public void addHeader(final String key, final String value) {
        exchange.getResponseHeaders().add(key, value);
    }

    public void setBody(final int status, final byte[] responseBytes) throws IOException {
        exchange.sendResponseHeaders(status, responseBytes.length);
        exchange.getResponseBody()
                .write(responseBytes);
    }

}
