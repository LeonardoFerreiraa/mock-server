package br.com.leonardoferreira.mockserver.integration.requesthandler;

import java.util.Collections;

import br.com.leonardoferreira.mockserver.RequestHandler;
import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.QueryParam;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Route;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.integration.domain.Customer;

public class FindAllCustomerRequestHandler implements RequestHandler {

    @Override
    public Route route() {
        return Route.builder()
                .method(HttpMethod.GET)
                .url("/customer?name=mario")
                .queryParams(
                        QueryParam.of("lastName", "armario")
                )
                .build();
    }

    @Override
    public Response handle(final Request request) {
        return Response.builder()
                .status(200)
                .body(Collections.singletonList(new Customer("mario")))
                .build();
    }

}
