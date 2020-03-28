package br.com.leonardoferreira.mockserver.entity;

import java.net.URI;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Url {

    private final String urn;

    private final List<QueryParam> queryParams;

    public static Url from(final String url) {
        final URI uri = URI.create(url);
        return new Url(uri.getPath(), QueryParam.from(uri.getQuery()));
    }

}
