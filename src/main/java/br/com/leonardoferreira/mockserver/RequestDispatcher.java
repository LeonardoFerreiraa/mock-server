package br.com.leonardoferreira.mockserver;

import java.util.ArrayList;
import java.util.List;

import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.matcher.RequestMatcher;
import br.com.leonardoferreira.mockserver.server.HttpEntity;
import br.com.leonardoferreira.mockserver.util.CollectionUtils;
import br.com.leonardoferreira.mockserver.util.Outcome;
import lombok.NonNull;

public class RequestDispatcher {

    private final List<RequestHandler> handlers = new ArrayList<>();

    public void addHandler(@NonNull final RequestHandler requestHandler) {
        this.handlers.add(requestHandler);
    }

    public void addHandlers(@NonNull final List<RequestHandler> handlers) {
        this.handlers.addAll(handlers);
    }

    public void cleanHandlers() {
        this.handlers.clear();
    }

    public void dispatch(final HttpEntity httpEntity) {
        final Request request = httpEntity.getRequest();

        final RequestHandler requestHandler = CollectionUtils.findFirst(
                handlers,
                handler -> RequestMatcher.from(handler.pattern()).match(request)
        );

        final Response response = dispatch(requestHandler, request);
        httpEntity.dispatchResponse(response);
    }

    private Response dispatch(final RequestHandler handler, final Request request) {
        if (handler == null) {
            return Response.notFound();
        }

        return Outcome.from(() -> handler.handle(request))
                .onErrorReturn(e -> Response.internalServerError(e.getMessage()));
    }
}
