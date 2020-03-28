package br.com.leonardoferreira.mockserver.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    private final int status;

    private final Object body;

    public static Response notFound() {
        return new Response(404, null);
    }

    public static Response internalServerError(final String message) {
        return new Response(500, message);
    }

}