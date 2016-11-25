package com.sebastian_daschner.jaxrs_analyzer.backend.table;

public interface TableAppender {

    TableAppender beginLine();

    TableAppender appendField(String field);

    TableAppender endLine();

    TableAppender endTable();

    String toString();
}
