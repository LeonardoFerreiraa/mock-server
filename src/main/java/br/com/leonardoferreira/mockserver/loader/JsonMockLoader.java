package br.com.leonardoferreira.mockserver.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.leonardoferreira.mockserver.RequestHandler;
import br.com.leonardoferreira.mockserver.RequestHandlerJournal;
import br.com.leonardoferreira.mockserver.MockServer;
import br.com.leonardoferreira.mockserver.entity.Header;
import br.com.leonardoferreira.mockserver.entity.HttpMethod;
import br.com.leonardoferreira.mockserver.entity.QueryParam;
import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;
import br.com.leonardoferreira.mockserver.entity.Route;
import br.com.leonardoferreira.mockserver.util.Json;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;

public final class JsonMockLoader {

    private JsonMockLoader() {
    }

    public static RequestHandlerJournal load(final String classpathPath) {
        final byte[] bytes = readClasspathResource(classpathPath);
        final MockDefinition definition = parseMockDefinition(bytes, classpathPath);
        final Route route = buildRoute(definition.getRoute());
        final Response response = buildResponse(definition.getResponse());
        return MockServer.addHandler(new RequestHandler() {
            @Override
            public Route route() {
                return route;
            }

            @Override
            public Response handle(final Request request) {
                return response;
            }
        });
    }

    private static byte[] readClasspathResource(final String path) {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new IllegalArgumentException("Mock file not found on classpath: " + path);
            }
            return toByteArray(stream);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to read mock file: " + path, e);
        }
    }

    private static byte[] toByteArray(final InputStream stream) throws IOException {
        final byte[] buffer = new byte[4096];
        final List<byte[]> chunks = new ArrayList<>();
        int totalRead = 0;
        int read = stream.read(buffer);
        while (read != -1) {
            final byte[] chunk = new byte[read];
            System.arraycopy(buffer, 0, chunk, 0, read);
            chunks.add(chunk);
            totalRead += read;
            read = stream.read(buffer);
        }
        final byte[] result = new byte[totalRead];
        int pos = 0;
        for (final byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, result, pos, chunk.length);
            pos += chunk.length;
        }
        return result;
    }

    @SneakyThrows
    private static MockDefinition parseMockDefinition(final byte[] bytes, final String path) {
        try {
            return Json.read(bytes, MockDefinition.class);
        } catch (final Exception e) {
            throw new RuntimeException("Failed to parse mock file: " + path, e);
        }
    }

    private static Route buildRoute(final MockDefinition.RouteDefinition routeDef) {
        final Route.Builder builder = Route.builder()
                .method(HttpMethod.valueOf(routeDef.getMethod().toUpperCase()))
                .url(routeDef.getUrl());

        if (routeDef.getQueryParams() == null || routeDef.getQueryParams().isEmpty()) {
            builder.queryParams();
        } else {
            final List<QueryParam> params = new ArrayList<>();
            for (final Map.Entry<String, String> entry : routeDef.getQueryParams().entrySet()) {
                params.add(QueryParam.of(entry.getKey(), entry.getValue()));
            }
            builder.queryParams(params.toArray(new QueryParam[0]));
        }

        if (routeDef.getHeaders() == null || routeDef.getHeaders().isEmpty()) {
            builder.headers();
        } else {
            final List<Header> headers = new ArrayList<>();
            for (final Map.Entry<String, String> entry : routeDef.getHeaders().entrySet()) {
                headers.add(Header.of(entry.getKey(), entry.getValue()));
            }
            builder.headers(headers.toArray(new Header[0]));
        }

        return builder.build();
    }

    private static Response buildResponse(final MockDefinition.ResponseDefinition responseDef) {
        final Response.Builder builder = Response.builder().status(responseDef.getStatus());

        if (responseDef.getBody() != null) {
            final JsonNode body = responseDef.getBody();
            if (body.isTextual()) {
                builder.body(body.asText());
            } else {
                builder.body(body);
            }
        }

        if (responseDef.getHeaders() != null && !responseDef.getHeaders().isEmpty()) {
            final List<Header> headers = new ArrayList<>();
            for (final Map.Entry<String, String> entry : responseDef.getHeaders().entrySet()) {
                headers.add(Header.of(entry.getKey(), entry.getValue()));
            }
            builder.headers(headers.toArray(new Header[0]));
        }

        return builder.build();
    }

}
