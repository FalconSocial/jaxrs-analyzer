package com.sebastian_daschner.jaxrs_analyzer.backend.table;

import com.sebastian_daschner.jaxrs_analyzer.backend.Backend;

public class CsvBackendBuilder implements Backend.BackendBuilder {
    @Override
    public Backend build() {
        return new TableBackend(new CsvAppender(), "CSV File", ".csv");
    }

}
