package com.sebastian_daschner.jaxrs_analyzer.backend.csv;

import com.sebastian_daschner.jaxrs_analyzer.backend.Backend;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.Project;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.ResourceMethod;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Comparator.comparing;

public class CsvBackend implements Backend {

    private final Lock lock = new ReentrantLock();
    private CsvAppender csvAppender;
    private Project project;

    @Override
    public String render(final Project project) {
        lock.lock();
        try {
            csvAppender = new CsvAppender();
            this.project = project;

            appendHeader();
            project.getResources().getResources().stream().sorted().forEach(this::appendResource);
            System.out.println(csvAppender.toString());
            return csvAppender.toString();
        } finally {
            lock.unlock();
        }
    }

    private void appendHeader() {
        csvAppender.appendField("Project");
        csvAppender.appendField("Tech");
        csvAppender.appendField("Class");
        csvAppender.appendField("Method");
        csvAppender.appendField("Path");
        csvAppender.appendField("Description");
        csvAppender.endLine();
    }

    private void appendResource(String resource) {
        project.getResources().getMethods(resource).stream()
                .sorted(comparing(ResourceMethod::getMethod))
                .forEach(resourceMethod -> {
                    csvAppender.appendField(project.getName());
                    csvAppender.appendField(resourceMethod.getTech().toString());
                    csvAppender.appendField(resourceMethod.getOriginalClass().replace('/', '.'));
                    csvAppender.appendField(resourceMethod.getMethod().toString());
                    csvAppender.appendField(project.getResources().getBasePath() + resource);
                    csvAppender.appendField(resourceMethod.getDescription());
                    csvAppender.endLine();
                });
    }

    @Override
    public String getName() {
        return "csv";
    }
}
