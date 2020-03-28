package br.com.leonardoferreira.mockserver.entity;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class Response {

    private final int status;

    private final Object body;

    private final List<Header> headers;

    public static Response notFound() {
        return new Response(404, null, null);
    }

    public static Response internalServerError(final String message) {
        return new Response(500, message, null);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int status;

        private Object body;

        private List<Header> headers;

        public Builder status(final int status) {
            this.status = status;
            return this;
        }

        public Builder body(final Object body) {
            this.body = body;
            return this;
        }

        public Builder headers(final Header... headers) {
            this.headers = Arrays.asList(headers);
            return this;
        }

        public Response build() {
            return new Response(status, body, headers);
        }

    }

}