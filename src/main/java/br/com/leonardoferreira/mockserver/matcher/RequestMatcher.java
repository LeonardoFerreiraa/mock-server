package br.com.leonardoferreira.mockserver.matcher;

import java.util.Collection;

import br.com.leonardoferreira.mockserver.entity.Header;
import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.QueryParam;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Route;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class RequestMatcher {

    private final Route route;

    public boolean match(final Request request) {
        final RequestMatcherChain requestMatcherChain = createRequestMatcherChain();

        return requestMatcherChain.check(request);
    }

    private RequestMatcherChain createRequestMatcherChain() {
        return new MethodRequestMatcherChain(
                new UrnRequestMatcher(
                        new QueryParamRequestMatcher(
                                new HeaderRequestMatcher(
                                        request -> true
                                )
                        )
                )
        );
    }

    interface RequestMatcherChain {
        boolean check(Request request);
    }

    @RequiredArgsConstructor
    class MethodRequestMatcherChain implements RequestMatcherChain {

        private final RequestMatcherChain next;

        @Override
        public boolean check(final Request request) {
            return methodMatches(request) && next.check(request);
        }

        private boolean methodMatches(final Request request) {
            return route.getMethod() == HttpMethod.ANY ||
                    route.getMethod() == request.getMethod();
        }

    }

    @RequiredArgsConstructor
    class UrnRequestMatcher implements RequestMatcherChain {

        private final RequestMatcherChain next;

        @Override
        public boolean check(final Request request) {
            return urnMatches(request) && next.check(request);
        }

        private boolean urnMatches(final Request request) {
            final UrnMatcher urnMatcher = route.getUrlMatcher();
            final String requestedUrn = request.getUrn();

            return urnMatcher.match(requestedUrn);
        }

    }

    @RequiredArgsConstructor
    class QueryParamRequestMatcher implements RequestMatcherChain {

        private final RequestMatcherChain next;

        @Override
        public boolean check(final Request request) {
            return queryParamMatches(request) && next.check(request);
        }

        private boolean queryParamMatches(final Request request) {
            final QueryParamMatcher queryParamMatch = route.getQueryParamMatcher();
            final Collection<QueryParam> requestedQueryParams = request.getQueryParams().values();

            return queryParamMatch.match(requestedQueryParams);
        }

    }

    @RequiredArgsConstructor
    class HeaderRequestMatcher implements RequestMatcherChain {

        private final RequestMatcherChain next;

        @Override
        public boolean check(final Request request) {
            return headerMatches(request) && next.check(request);
        }

        private boolean headerMatches(final Request request) {
            final HeaderMatcher headerMatcher = route.toHeaderMatcher();
            final Collection<Header> requestedHeaders = request.getHeaders().values();

            return headerMatcher.match(requestedHeaders);
        }

    }

}
