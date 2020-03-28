package br.com.leonardoferreira.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Request {

    private final HttpMethod method;

    private final String url;

}
