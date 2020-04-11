package br.com.leonardoferreira.mockserver;

import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Route;
import br.com.leonardoferreira.mockserver.entity.Response;

public interface RequestHandler {

    Route route();

    Response handle(Request request);

}
