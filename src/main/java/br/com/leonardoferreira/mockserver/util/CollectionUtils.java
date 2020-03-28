package br.com.leonardoferreira.mockserver.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {

    public static <T> List<T> concat(final List<T> first, final List<T> second) {
        final List<T> result = new ArrayList<>();

        if (first != null) {
            result.addAll(first);
        }

        if (second != null) {
            result.addAll(second);
        }

        return result;
    }

}
