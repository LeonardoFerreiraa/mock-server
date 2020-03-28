package br.com.leonardoferreira.mockserver.util;

public class StringUtils {

    public static Pair<String, String> toPair(final String str, final String splitBy) {
        final String[] split = str.split(splitBy, 2);

        if (split.length == 0) {
            return Pair.empty();
        }

        if (split.length == 1) {
            return Pair.from(split[0], null);
        }

        return Pair.from(split[0], split[1]);
    }

}
