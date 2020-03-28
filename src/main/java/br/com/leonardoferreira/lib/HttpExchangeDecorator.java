package br.com.leonardoferreira.lib;

import java.io.IOException;

import br.com.leonardoferreira.user.HttpMethod;
import br.com.leonardoferreira.user.Request;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class HttpExchangeDecorator {

    private final HttpExchange exchange;

    public Request toRequest() {
        return Request.builder()
                .method(HttpMethod.valueOf(exchange.getRequestMethod()))
                .url(exchange.getRequestURI().toString())
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
