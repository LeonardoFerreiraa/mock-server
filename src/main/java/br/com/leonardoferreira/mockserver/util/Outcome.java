package br.com.leonardoferreira.mockserver.util;

import java.util.concurrent.Callable;
import java.util.function.Function;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Outcome<R, E> {

    private final R result;

    private final E error;

    public static <R, E> Outcome<R, E> success(@NonNull final R result) {
        return new Outcome<>(result, null);
    }

    public static <R, E> Outcome<R, E> error(@NonNull final E error) {
        return new Outcome<>(null, error);
    }

    public boolean hasResult() {
        return result != null;
    }

    public boolean hasError() {
        return error != null;
    }

    public static <T> Outcome<T, Exception> from(final Callable<T> callable) {
        try {
            return Outcome.success(callable.call());
        } catch (final Exception e) {
            e.printStackTrace();
            return Outcome.error(e);
        }
    }

    public R onErrorReturn(final Function<E, R> mapError) {
        if (hasResult()) {
            return result;
        }

        if (hasError()) {
            return mapError.apply(error);
        }

        throw new IllegalStateException();
    }
}