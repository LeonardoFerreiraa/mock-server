package br.com.leonardoferreira.mockserver.integration.requesthandler;

import br.com.leonardoferreira.mockserver.RequestHandler;
import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.entity.Route;

public class CreateCustomerRequestHandler implements RequestHandler {

    @Override
    public Route route() {
        return Route.builder()
                .method(HttpMethod.POST)
                .url("/customer")
                .build();
    }

    @Override
    public Response handle(final Request request) {
        return Response.builder()
                .status(201)
                .build();
    }
}
