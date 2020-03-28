package br.com.leonardoferreira.mockserver.matcher;

public class UrnMatcher {

    private final String regex;

    public static UrnMatcher from(final String path) {
        return new UrnMatcher(path);
    }

    private UrnMatcher(final String path) {
        final String normalizedPath = normalizePath(path);
        this.regex = normalizedPath.replaceAll("\\{\\w+}/", "\\\\w+/");
    }

    private static String normalizePath(final String path) {
        final StringBuilder sb = new StringBuilder();
        if (!path.startsWith("/")) {
            sb.append("/");
        }

        sb.append(path);

        if (!path.endsWith("/")) {
            sb.append("/");
        }

        return sb.toString();
    }

    public boolean match(final String url) {
        return normalizePath(url).matches(regex);
    }

}
