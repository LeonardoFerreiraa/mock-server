package br.com.leonardoferreira.mockserver.server;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
class SunWebServer implements WebServer {

    private final WebServerConfig config;

    private final HttpHandler httpHandler;

    private HttpServer httpServer;

    @Override
    @SneakyThrows
    public void start() {
        httpServer = HttpServer.create(
                new InetSocketAddress(config.getHostname(), config.getPort()),
                0
        );

        httpServer.createContext(
                config.getBasePath(),
                exchange -> httpHandler.handle(SunHttpEntity.from(exchange))
        );

        httpServer.start();
    }

    @Override
    public void stop() {
        httpServer.stop(0);
    }

}
