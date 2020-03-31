package br.com.leonardoferreira.mockserver.matcher;

import java.util.List;

import br.com.leonardoferreira.mockserver.entity.Header;
import br.com.leonardoferreira.mockserver.entity.QueryParam;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.RequestPattern;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class RequestMatcher {

    private final RequestPattern requestPattern;

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
            return requestPattern.getMethod() == request.getMethod();
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
            final UrnMatcher urnMatcher = requestPattern.getUrlMatcher();
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
            final QueryParamMatcher queryParamMatch = requestPattern.getQueryParamMatcher();
            final List<QueryParam> requestedQueryParams = request.getQueryParams();

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
            final HeaderMatcher headerMatcher = requestPattern.toHeaderMatcher();
            final List<Header> requestedHeaders = request.getHeaders();

            return headerMatcher.match(requestedHeaders);
        }

    }

}
