package br.com.leonardoferreira.mockserver;

import java.io.IOException;
import java.util.List;

import br.com.leonardoferreira.mockserver.decorator.HttpExchangeDecorator;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.matcher.RequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DefaultHttpHandler implements HttpHandler {

    private final ObjectMapper objectMapper;

    private final List<RequestHandler> handlers;

    public DefaultHttpHandler(final List<RequestHandler> handlers) {
        this(new ObjectMapper(), handlers);
    }

    public DefaultHttpHandler(final ObjectMapper objectMapper, final List<RequestHandler> handlers) {
        this.objectMapper = objectMapper;
        this.handlers = handlers;
    }

    @Override
    public void handle(final HttpExchange exchange) throws IOException {
        try {
            handle(HttpExchangeDecorator.from(exchange));
        } catch (final Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void handle(final HttpExchangeDecorator exchange) throws IOException {
        final Request request = exchange.toRequest();

        final Response response = handlers.stream()
                .filter(handler -> RequestMatcher.from(handler.pattern()).match(request))
                .map(handler -> handler.handle(request))
                .findFirst()
                .orElse(Response.notFound());

        exchange.addHeader("Content-Type", "application/json");
        exchange.setBody(response.getStatus(), objectMapper.writeValueAsBytes(response.getBody()));
    }


}
