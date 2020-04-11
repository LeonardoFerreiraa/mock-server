package br.com.leonardoferreira.mockserver;

import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Route;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.util.Outcome;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class RequestHandlerDecorator {

    private final RequestHandler handler;

    @Getter
    private final RequestHandlerJournal journal;

    public static RequestHandlerDecorator from(final RequestHandler handler) {
        return new RequestHandlerDecorator(handler, new RequestHandlerJournal());
    }

    public Route route() {
        return handler.route();
    }

    public Response handle(final Request request) {
        final Response response = Outcome.from(() -> handler.handle(request))
                .onErrorReturn(e -> Response.internalServerError(e.getMessage()));

        journal.registerEntry(request, response);

        return response;
    }

}
