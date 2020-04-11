package br.com.leonardoferreira.mockserver.entity;

import br.com.leonardoferreira.mockserver.util.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Request {

    private final HttpMethod method;

    private final Url url;

    private final String urn;

    private final Headers headers;

    private final byte[] body;

    public String getUrn() {
        return url.getUrn();
    }

    public QueryParams getQueryParams() {
        return url.getQueryParams();
    }

    public Header findHeader(final String header) {
        return headers.get(header);
    }

    public QueryParam findQueryParam(final String queryParam) {
        return getQueryParams().get(queryParam);
    }

    public <T> T bodyAs(final Class<T> clazz) {
        return Json.read(body, clazz);
    }

}
