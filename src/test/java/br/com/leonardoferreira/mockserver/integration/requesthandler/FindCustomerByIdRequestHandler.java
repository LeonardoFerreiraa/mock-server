package br.com.leonardoferreira.mockserver.integration.requesthandler;

import br.com.leonardoferreira.mockserver.RequestHandler;
import br.com.leonardoferreira.mockserver.entity.Header;
import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Route;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.integration.domain.Customer;

public class FindCustomerByIdRequestHandler implements RequestHandler {

    @Override
    public Route route() {
        return Route.builder()
                .method(HttpMethod.GET)
                .url("/customer/{id}")
                .headers(
                        Header.of("x-details", "true")
                )
                .build();
    }

    @Override
    public Response handle(final Request request) {
        return Response.builder()
                .status(200)
                .body(new Customer("Mario"))
                .headers(
                        Header.of("x-custom-header", "customvalue")
                )
                .build();
    }

}