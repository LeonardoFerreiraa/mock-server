package br.com.leonardoferreira.mockserver;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpServer;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(staticName = "create")
public class MockServer {

    private String hostname = "localhost";

    private int port = 8080;

    private String basePath = "/";

    private List<RequestHandler> handlers = new ArrayList<>();

    private HttpServer server;

    @SneakyThrows
    public MockServer start() {
        server = HttpServer.create(
                new InetSocketAddress(hostname, port),
                0
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
