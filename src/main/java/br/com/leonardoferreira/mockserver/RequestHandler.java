package br.com.leonardoferreira.mockserver;

import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.RequestPattern;
import br.com.leonardoferreira.mockserver.entity.Response;

public interface RequestHandler {

    RequestPattern pattern();

    Response handle(Request request);

}
