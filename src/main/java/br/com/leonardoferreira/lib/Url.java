package br.com.leonardoferreira.lib;

import java.net.URI;
import java.util.List;

import br.com.leonardoferreira.user.QueryParam;
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
