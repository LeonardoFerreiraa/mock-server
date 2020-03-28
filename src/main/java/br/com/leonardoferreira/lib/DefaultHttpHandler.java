package br.com.leonardoferreira.lib;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.leonardoferreira.user.Request;
import br.com.leonardoferreira.user.RequestHandler;
import br.com.leonardoferreira.user.RequestMatcher;
import br.com.leonardoferreira.user.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DefaultHttpHandler implements HttpHandler {

    private final ObjectMapper objectMapper;

    private final Map<RequestMatcher, RequestHandler> handlers;

    public DefaultHttpHandler(final List<RequestHandler> handlers) {
        this(new ObjectMapper(), handlers);
    }

    public DefaultHttpHandler(final ObjectMapper objectMapper, final List<RequestHandler> handlers) {
        this.objectMapper = objectMapper;
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(RequestHandler::matcher, Function.identity()));
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

        final Response response = handlers.entrySet()
                .stream()
                .filter(entry -> match(entry.getKey(), request))
                .map(entry -> entry.getValue().handle(request))
                .findFirst()
                .orElse(Response.notFound());
        exchange.addHeader("Content-Type", "application/json");
        exchange.setBody(response.getStatus(), objectMapper.writeValueAsBytes(response.getBody()));
    }

    private boolean match(final RequestMatcher requestMatcher, final Request request) {
        if (requestMatcher.getMethod() == request.getMethod()) {
            final Url requestedUrl = Url.from(request.getUrl());

            if (requestMatcher.getUrlMatcher().match(requestedUrl.getUrn())) {
                if (requestMatcher.getQueryParamMatch().match(requestedUrl.getQueryParams())) {
                    return true;
                }
            }
        }

        return false;
    }


}
