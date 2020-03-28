package br.com.leonardoferreira.user;

public interface RequestHandler {

    RequestMatcher matcher();

    Response handle(Request request);

}
