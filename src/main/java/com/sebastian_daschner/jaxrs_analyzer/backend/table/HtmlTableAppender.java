package com.sebastian_daschner.jaxrs_analyzer.backend.table;

public class HtmlTableAppender implements TableAppender {

    private final StringBuffer buf = new StringBuffer();

    public HtmlTableAppender() {
        buf.append("<html><body><table border=\"1\">\n");
    }

    @Override
    public TableAppender beginLine() {
        buf.append("  <tr>");
        return this;
    }

    public HtmlTableAppender appendField(String field) {
        buf.append("<td>");
        if(field == null) {
            // append nothing
        } else {
            buf.append(field);
        }
        buf.append("</td>");
        return this;
    }

    public HtmlTableAppender endLine() {
        buf.append("</tr>\n");
        return this;
    }

    @Override
    public TableAppender endTable() {
        buf.append("</table></body>");
        return this;
    }

    @Override
    public String toString() {
        return buf.toString();
    }
}
