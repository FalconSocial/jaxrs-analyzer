package com.sebastian_daschner.jaxrs_analyzer.backend.table;

import com.sebastian_daschner.jaxrs_analyzer.backend.Backend;

public class HtmlTableBackendBuilder implements Backend.BackendBuilder {
    @Override
    public Backend build() {
        return new TableBackend(new HtmlTableAppender(), "HTML table", " table.html");
    }

}
