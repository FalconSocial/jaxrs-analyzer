package com.sebastian_daschner.jaxrs_analyzer.backend.table;

import java.util.regex.Pattern;

public class CsvAppender implements TableAppender {

    private static final Pattern PATTERN_TO_ESCAPE = Pattern.compile("[,\r\n\"]");
    private final StringBuffer buf = new StringBuffer();
    private String delimiter = "";

    @Override
    public TableAppender beginLine() {
        return this;
    }

    public CsvAppender appendField(String field) {
        buf.append(delimiter);
        if(field == null) {
            // append nothing
        } else if(PATTERN_TO_ESCAPE.matcher(field).find()) {
            buf.append('"');
            for(int i = 0; i < field.length(); i++) {
                char c = field.charAt(i);
                if(c == '"') {
                    buf.append('"');
                }
                buf.append(c);
            }
            buf.append('"');
        } else {
            buf.append(field);
        }
        delimiter = ",";
        return this;
    }

    public CsvAppender endLine() {
        buf.append("\n");
        delimiter = "";
        return this;
    }

    @Override
    public TableAppender endTable() {
        return this;
    }

    @Override
    public String toString() {
        return buf.toString();
    }
}
