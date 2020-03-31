package br.com.leonardoferreira.mockserver.server;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class WebServerConfig {

    private final String basePath;

    private final String hostname;

    private final int port;

}
