package com.sebastian_daschner.jaxrs_analyzer.backend.apib;

import com.sebastian_daschner.jaxrs_analyzer.backend.Backend;

public class ApibBackendBuilder implements Backend.BackendBuilder {
    @Override
    public Backend build() {
        return new ApibBackend();
    }

}
