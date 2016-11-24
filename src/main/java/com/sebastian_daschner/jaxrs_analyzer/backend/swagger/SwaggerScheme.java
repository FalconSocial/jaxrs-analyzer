package com.sebastian_daschner.jaxrs_analyzer.backend.swagger;

import com.sebastian_daschner.jaxrs_analyzer.backend.BackendType;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Swagger transfer protocols.
 *
 * @author Sebastian Daschner
 */
public enum SwaggerScheme {

    HTTP, HTTPS, WS, WSS;

    public static SwaggerScheme parse(final String scheme) {
        try {
            return SwaggerScheme.valueOf(scheme.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Swagger scheme `" + scheme + "` not valid. Valid values are: " +
                    Stream.of(SwaggerScheme.values()).map(Enum::name).map(String::toLowerCase).collect(Collectors.joining(", ")));
        }
    }
}
