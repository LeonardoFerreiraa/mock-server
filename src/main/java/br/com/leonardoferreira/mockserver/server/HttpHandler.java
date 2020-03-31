package br.com.leonardoferreira.mockserver.server;

public interface HttpHandler {

    void handle(HttpEntity httpExchange);

}
