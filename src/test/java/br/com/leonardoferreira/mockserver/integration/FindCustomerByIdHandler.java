package br.com.leonardoferreira.mockserver.integration;

import br.com.leonardoferreira.mockserver.RequestHandler;
import br.com.leonardoferreira.mockserver.entity.Header;
import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.RequestPattern;
import br.com.leonardoferreira.mockserver.entity.Response;

public class FindCustomerByIdHandler implements RequestHandler {

    @Override
    public RequestPattern pattern() {
        return RequestPattern.builder()
                .method(HttpMethod.GET)
                .url("/customer/{id}")
                .headers(
                        Header.of("x-details", "true")
                )
                .build();
    }

    @Override
    public Response handle(final Request request) {
        final Customer response = new Customer("Mario");

        return Response.builder()
                .status(200)
                .body(response)
                .headers(
                        Header.of("x-custom-header", "customvalue")
                )
                .build();
    }


}