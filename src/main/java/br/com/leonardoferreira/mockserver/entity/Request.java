package br.com.leonardoferreira.mockserver.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Request {

    private final HttpMethod method;

    private final Url url;

    private final List<Header> headers;

}
