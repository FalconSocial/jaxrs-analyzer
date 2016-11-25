package com.sebastian_daschner.jaxrs_analyzer.backend.table;

import com.sebastian_daschner.jaxrs_analyzer.backend.Backend;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.Project;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.ResourceMethod;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Comparator.comparing;

public class TableBackend implements Backend {

    private final Lock lock = new ReentrantLock();
    private Project project;
    private TableAppender tableAppender;
    private String name;
    private String fileExtension;

    public TableBackend(TableAppender tableAppender, String name, String fileExtension) {
        this.tableAppender = tableAppender;
        this.name = name;
        this.fileExtension = fileExtension;
    }

    @Override
    public String render(final Project project) {
        lock.lock();
        try {
            this.project = project;

            appendHeader();
            project.getResources().getResources().stream().sorted().forEach(this::appendResource);
            return tableAppender.toString();
        } finally {
            lock.unlock();
        }
    }

    private void appendHeader() {
        tableAppender.beginLine();
        tableAppender.appendField("Project");
        tableAppender.appendField("Tech");
        tableAppender.appendField("Class");
        tableAppender.appendField("Method");
        tableAppender.appendField("Path");
        tableAppender.appendField("Description");
        tableAppender.endLine();
    }

    private void appendResource(String resource) {
        project.getResources().getMethods(resource).stream()
                .sorted(comparing(ResourceMethod::getMethod))
                .forEach(resourceMethod -> {
                    tableAppender.beginLine();
                    tableAppender.appendField(project.getName());
                    tableAppender.appendField(resourceMethod.getTech().toString());
                    tableAppender.appendField(resourceMethod.getOriginalClass().replace('/', '.'));
                    tableAppender.appendField(resourceMethod.getMethod().toString());
                    tableAppender.appendField(project.getResources().getBasePath() + resource);
                    tableAppender.appendField(resourceMethod.getDescription());
                    tableAppender.endLine();
                });
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getOutputFile(Project project) {
        return project.getName() + fileExtension;
    }

}
