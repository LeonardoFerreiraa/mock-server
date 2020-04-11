package br.com.leonardoferreira.mockserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.leonardoferreira.mockserver.entity.Request;
import br.com.leonardoferreira.mockserver.entity.Response;

public class RequestHandlerJournal {

    private final List<RequestHandlerJournalEntry> entries = new ArrayList<>();

    void registerEntry(final Request request, final Response response) {
        entries.add(RequestHandlerJournalEntry.of(request, response));
    }

    public int requestCount() {
        return entries.size();
    }

    public List<RequestHandlerJournalEntry> entries() {
        return Collections.unmodifiableList(entries);
    }

    public Request firstRequest() {
        if (entries.isEmpty()) {
            throw new AssertionError("No requests found to this dispatcher");
        }

        final RequestHandlerJournalEntry firstEntry = entries.get(0);
        return firstEntry.getRequest();
    }

    public List<Request> requests() {
        return Collections.unmodifiableList(
                entries.stream()
                        .map(RequestHandlerJournalEntry::getRequest)
                        .collect(Collectors.toList())
        );
    }

}
