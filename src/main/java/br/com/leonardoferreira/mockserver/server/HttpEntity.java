package br.com.leonardoferreira.mockserver.server;

import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;

public interface HttpEntity {

    Request getRequest();

    void dispatchResponse(Response response);

}
