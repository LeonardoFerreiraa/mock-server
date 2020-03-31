package br.com.leonardoferreira.mockserver;

import java.net.InetSocketAddress;
import java.util.Arrays;

import br.com.leonardoferreira.mockserver.decorator.HttpExchangeDecorator;
import com.sun.net.httpserver.HttpServer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockServer {

    private static String hostname = "localhost";

    private static int port = 8080;

    private static String basePath = "/";

    private static RequestDispatcher requestDispatcher = new RequestDispatcher();

    private static HttpServer server;

    @SneakyThrows
    public static void start() {
        server = HttpServer.create(
                new InetSocketAddress(hostname, port),
                0
        );

        server.createContext(
                basePath,
                exchange -> requestDispatcher.dispatch(HttpExchangeDecorator.from(exchange))
        );

        server.start();
    }

    public static void hostname(final String hostname) {
        MockServer.hostname = hostname;
    }

    public static void port(final int port) {
        MockServer.port = port;
    }

    public static void basePath(final String basePath) {
        MockServer.basePath = basePath;
    }

    public static void addHandler(final RequestHandler requestHandler) {
        MockServer.requestDispatcher.addHandler(requestHandler);
    }

    public static void addHandlers(final RequestHandler... requestHandler) {
        MockServer.requestDispatcher.addHandlers(Arrays.asList(requestHandler));
    }

    public static void cleanHandlers() {
        MockServer.requestDispatcher.cleanHandlers();
    }

    public static void stop() {
        server.stop(0);
    }

}
