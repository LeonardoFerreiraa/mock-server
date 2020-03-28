package br.com.leonardoferreira.integration;

import br.com.leonardoferreira.user.HttpMethod;
import br.com.leonardoferreira.user.Request;
import br.com.leonardoferreira.user.RequestHandler;
import br.com.leonardoferreira.user.RequestMatcher;
import br.com.leonardoferreira.user.Response;

public class FindCustomerByIdHandler implements RequestHandler {

    @Override
    public RequestMatcher matcher() {
        return RequestMatcher.builder()
                .method(HttpMethod.GET)
                .url("/customer/{id}")
                .build();
    }

    @Override
    public Response handle(final Request request) {
        final Customer response = new Customer("Mario");

        return Response.builder()
                .status(200)
                .body(response)
                .build();
    }


}