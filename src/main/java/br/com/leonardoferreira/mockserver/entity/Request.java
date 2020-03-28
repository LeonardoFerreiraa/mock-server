package br.com.leonardoferreira.mockserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Request {

    private final HttpMethod method;

    private final Url url;

}
