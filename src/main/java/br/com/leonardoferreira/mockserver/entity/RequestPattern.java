package br.com.leonardoferreira.mockserver.entity;

import java.util.Arrays;
import java.util.List;

import br.com.leonardoferreira.mockserver.matcher.QueryParamMatcher;
import br.com.leonardoferreira.mockserver.matcher.UrnMatcher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestPattern {

    private final HttpMethod method;

    private final String url;

    private final List<QueryParam> queryParams;

    public static Builder builder() {
        return new Builder();
    }

    public UrnMatcher getUrlMatcher() {
        return UrnMatcher.from(getUrl());
    }

    public QueryParamMatcher getQueryParamMatch() {
        return QueryParamMatcher.from(queryParams);
    }

    @NoArgsConstructor
    public static class Builder {

        private HttpMethod method;

        private String url;

        private List<QueryParam> queryParams;

        public Builder method(final HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder url(final String url) {
            this.url = url;
            return this;
        }

        public Builder queryParams(final QueryParam... queryParams) {
            this.queryParams = Arrays.asList(queryParams);
            return this;
        }

        public RequestPattern build() {
            return new RequestPattern(method, url, queryParams);
        }

    }

}
