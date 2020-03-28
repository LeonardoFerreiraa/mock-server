package br.com.leonardoferreira.integration;

import java.util.Collections;

import br.com.leonardoferreira.user.HttpMethod;
import br.com.leonardoferreira.user.QueryParam;
import br.com.leonardoferreira.user.Request;
import br.com.leonardoferreira.user.RequestHandler;
import br.com.leonardoferreira.user.RequestMatcher;
import br.com.leonardoferreira.user.Response;

public class FindAllCustomerHandler implements RequestHandler {

    @Override
    public RequestMatcher matcher() {
        return RequestMatcher.builder()
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
