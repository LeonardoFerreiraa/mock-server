package br.com.leonardoferreira.mockserver.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "from")
public class Pair<A, B> {

    private final A first;

    private final B second;

    public static <A, B> Pair<A, B> empty() {
        return new Pair<>(null, null);
    }

}
