package br.com.leonardoferreira.mockserver.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import lombok.SneakyThrows;

public final class IOUtils {

    private IOUtils() {
    }

    @SneakyThrows
    @SuppressWarnings("PMD.AssignmentInOperand")
    public static byte[] inputStreamToByteArray(final InputStream inputStream) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        return outputStream.toByteArray();
    }

}
