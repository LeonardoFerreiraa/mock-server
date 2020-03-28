package br.com.leonardoferreira.util;

import lombok.SneakyThrows;

public class Try {

    @SneakyThrows
    public static <T> T silently(final Runnable<T> runnable) {
        return runnable.run();
    }

    @FunctionalInterface
    public interface Runnable<T> {
        T run() throws Throwable;
    }

}
