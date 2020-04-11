package br.com.leonardoferreira.mockserver;

import java.util.ArrayList;
import java.util.List;

import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.matcher.RequestMatcher;
import br.com.leonardoferreira.mockserver.server.HttpEntity;
import br.com.leonardoferreira.mockserver.util.CollectionUtils;
import lombok.NonNull;

public class RequestDispatcher {

    private final List<RequestHandlerDecorator> handlers = new ArrayList<>();

    public RequestHandlerJournal addHandler(@NonNull final RequestHandler requestHandler) {
        final RequestHandlerDecorator requestHandlerDecorator = RequestHandlerDecorator.of(requestHandler);
        handlers.add(requestHandlerDecorator);

        return requestHandlerDecorator.getJournal();
    }

    public void cleanHandlers() {
        this.handlers.clear();
    }

    public void dispatch(final HttpEntity httpEntity) {
        final Request request = httpEntity.getRequest();

        final RequestHandlerDecorator requestHandler = CollectionUtils.findFirst(
                handlers,
                handler -> RequestMatcher.from(handler.route()).match(request)
        );

        final Response response = dispatch(requestHandler, request);
        httpEntity.dispatchResponse(response);
    }

    private Response dispatch(final RequestHandlerDecorator requestHandler, final Request request) {
        if (requestHandler == null) {
            return Response.notFound();
        }

        return requestHandler.handle(request);
    }

}
