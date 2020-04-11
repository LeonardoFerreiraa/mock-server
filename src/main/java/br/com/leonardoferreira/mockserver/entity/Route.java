package br.com.leonardoferreira.mockserver.entity;

import java.util.Arrays;
import java.util.List;

import br.com.leonardoferreira.mockserver.matcher.HeaderMatcher;
import br.com.leonardoferreira.mockserver.matcher.QueryParamMatcher;
import br.com.leonardoferreira.mockserver.matcher.UrnMatcher;
import br.com.leonardoferreira.mockserver.util.CollectionUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Route {

    private final HttpMethod method;

    private final Url url;

    private final List<QueryParam> queryParams;

    private final List<Header> headers;

    public static Builder builder() {
        return new Builder();
    }

    public UrnMatcher getUrlMatcher() {
        return UrnMatcher.from(url.getUrn());
    }

    public QueryParamMatcher getQueryParamMatcher() {
        return QueryParamMatcher.from(CollectionUtils.concat(queryParams, url.getQueryParams().values()));
    }

    public HeaderMatcher toHeaderMatcher() {
        return HeaderMatcher.from(headers);
    }

    public static class Builder {

        private HttpMethod method;

        private Url url;

        private List<QueryParam> queryParams;

        private List<Header> headers;

        public Builder method(final HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder url(final String url) {
            this.url = Url.from(url);
            return this;
        }

        public Builder queryParams(final QueryParam... queryParams) {
            this.queryParams = Arrays.asList(queryParams);
            return this;
        }

        public Builder headers(final Header... headers) {
            this.headers = Arrays.asList(headers);
            return this;
        }

        public Route build() {
            return new Route(method, url, queryParams, headers);
        }

    }

}
