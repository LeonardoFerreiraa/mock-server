package br.com.leonardoferreira.mockserver;

import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class RequestHandlerJournalEntry {

    private final Request request;

    private final Response response;

}
