package br.com.leonardoferreira.mockserver.server;

public interface WebServer {

    static WebServer create(final WebServerConfig config, final HttpHandler httpHandler) {
        return new SunWebServer(config, httpHandler);
    }

    void start();

    void stop();

}
