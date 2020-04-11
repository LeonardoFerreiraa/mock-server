package br.com.leonardoferreira.mockserver.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class QueryParam {

    private final String key;

    private final String value;

}
