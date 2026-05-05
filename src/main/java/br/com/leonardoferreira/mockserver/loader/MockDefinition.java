package br.com.leonardoferreira.mockserver.loader;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class MockDefinition {

    private RouteDefinition route;

    private ResponseDefinition response;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class RouteDefinition {

        private String method;

        private String url;

        private Map<String, String> headers;

        private Map<String, String> queryParams;

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ResponseDefinition {

        private int status;

        private Map<String, String> headers;

        private JsonNode body;

    }

}
