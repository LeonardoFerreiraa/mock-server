package br.com.leonardoferreira.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    private final int status;

    private final Object body;

    public static Response notFound() {
        return new NotFound();
    }

    public static class NotFound extends Response {
        public NotFound() {
            super(404, null);
        }
    }

}
