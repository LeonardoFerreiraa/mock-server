package br.com.leonardoferreira.mockserver;

import java.io.IOException;
import java.util.List;

import br.com.leonardoferreira.mockserver.decorator.HttpExchangeDecorator;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.matcher.RequestMatcher;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class DefaultHttpHandler implements HttpHandler {

    private final List<RequestHandler> handlers;

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
        final HttpExchangeDecorator exchange = HttpExchangeDecorator.from(httpExchange);

        try (exchange) {
            exchange.respond(handle(exchange));
        } catch (final Exception e) {
            e.printStackTrace();
            exchange.respond(Response.internalServerError(e.getMessage()));
        }
    }

    private Response handle(final HttpExchangeDecorator exchange) {
        final Request request = exchange.toRequest();

        return handlers.stream()
                .filter(handler -> RequestMatcher.from(handler.pattern()).match(request))
                .findFirst()
                .map(handler -> handler.handle(request))
                .orElse(Response.notFound());
    }

}
