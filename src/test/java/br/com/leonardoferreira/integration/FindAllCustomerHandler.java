package br.com.leonardoferreira.integration;

import java.util.Collections;

import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.QueryParam;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.RequestHandler;
import br.com.leonardoferreira.mockserver.entity.RequestPattern;
import br.com.leonardoferreira.mockserver.entity.Response;

public class FindAllCustomerHandler implements RequestHandler {

    @Override
    public RequestPattern pattern() {
        return RequestPattern.builder()
                .method(HttpMethod.GET)
                .url("/customer")
                .queryParams(QueryParam.of(
                        "name", "mario"
                ))
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
