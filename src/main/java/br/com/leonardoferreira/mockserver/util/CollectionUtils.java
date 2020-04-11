package br.com.leonardoferreira.mockserver.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static <T> List<T> concat(final Collection<T> first, final Collection<T> second) {
        final List<T> result = new ArrayList<>();

        if (first != null) {
            result.addAll(first);
        }

        if (second != null) {
            result.addAll(second);
        }

        return result;
    }

    public static <T> T findFirst(final List<T> elements, final Predicate<T> predicate) {
        for (final T element : elements) {
            if (predicate.test(element)) {
                return element;
            }
        }

        return null;
    }

}
