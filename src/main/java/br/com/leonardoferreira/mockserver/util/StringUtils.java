package br.com.leonardoferreira.mockserver.util;

import java.util.Objects;

public final class StringUtils {

    private StringUtils() {
    }

    public static Pair<String, String> toPair(final String str, final String splitBy) {
        final String[] split = str.split(splitBy, 2);

        if (split.length == 0) {
            return Pair.empty();
        }

        if (split.length == 1) {
            return Pair.of(split[0], null);
        }

        return Pair.of(split[0], split[1]);
    }

    public static boolean equalsIgnoreCase(final String a, final String b) {
        return Objects.equals(a, b) || a != null && a.equalsIgnoreCase(b);
    }

}
