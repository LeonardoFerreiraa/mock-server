package br.com.leonardoferreira.user;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import br.com.leonardoferreira.lib.DefaultHttpHandler;
import br.com.leonardoferreira.util.Try;
import com.sun.net.httpserver.HttpServer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockServer {

    private String hostname = "localhost";

    private int port = 8080;

    private String basePath = "/";

    private List<RequestHandler> handlers = new ArrayList<>();

    private HttpServer server;

    public static MockServer create() {
        return new MockServer();
    }

    public MockServer start() {
        server = Try.silently(() ->
                HttpServer.create(
                        new InetSocketAddress(hostname, port),
                        0
                )
        );

        final DefaultHttpHandler httpHandler = new DefaultHttpHandler(handlers);
        server.createContext(basePath, httpHandler);

        server.start();

        return this;
    }

    public MockServer hostname(final String hostname) {
        this.hostname = hostname;
        return this;
    }

    public MockServer port(final int port) {
        this.port = port;
        return this;
    }

    public MockServer basePath(final String basePath) {
        this.basePath = basePath;
        return this;
    }

    public MockServer addHandler(final RequestHandler requestHandler) {
        this.handlers.add(requestHandler);
        return this;
    }

    public MockServer clearHandlers() {
        this.handlers.clear();
        return this;
    }

    public void stop() {
        server.stop(0);
    }
}
