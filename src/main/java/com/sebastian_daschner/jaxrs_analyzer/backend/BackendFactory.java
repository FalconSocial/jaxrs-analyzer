package com.sebastian_daschner.jaxrs_analyzer.backend;

import com.sebastian_daschner.jaxrs_analyzer.backend.swagger.SwaggerBackendBuilder;
import com.sebastian_daschner.jaxrs_analyzer.backend.swagger.SwaggerScheme;
import com.sebastian_daschner.jaxrs_analyzer.utils.StringUtils;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BackendFactory {

    public Set<Backend> fromTypes(Set<BackendType> backendTypes, Map<String, String> options) {
        return backendTypes.stream().map(backendType -> fromType(backendType, options)).collect(Collectors.toSet());
    }

    public Backend fromType(BackendType backendType, Map<String, String> options) {
        switch (backendType) {
            case SWAGGER:
                return configureSwaggerBackend(options);
            case PLAINTEXT:
                return Backend.plainText().build();
            case ASCIIDOC:
                return Backend.asciiDoc().build();
            case CSV:
                return Backend.csv().build();
            case APIB:
                return Backend.apib().build();
            default:
                // can not happen
                throw new IllegalArgumentException("Unknown backend type " + backendType);
        }
    }

    private Backend configureSwaggerBackend(Map<String, String> options) {
        final SwaggerBackendBuilder builder = Backend.swagger();

        builder.domain(options.getOrDefault("domain", "example.com"));
        builder.schemes(StringUtils.parseEnumSet(SwaggerScheme.class, options.getOrDefault("swaggerSchemes", "http"), "swagger scheme"));
        builder.renderTags(Boolean.parseBoolean(options.getOrDefault("renderSwaggerTags", "false")),
                Integer.parseInt(options.getOrDefault("swaggerTagsPathOffset", "0")));
        return builder.build();
    }
}
