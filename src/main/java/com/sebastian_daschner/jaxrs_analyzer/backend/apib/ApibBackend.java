package com.sebastian_daschner.jaxrs_analyzer.backend.apib;

import com.sebastian_daschner.jaxrs_analyzer.backend.Backend;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.Project;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.ResourceMethod;
import com.sebastian_daschner.jaxrs_analyzer.utils.Pair;

import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.Comparator;

public class ApibBackend implements Backend {

    private final Lock lock = new ReentrantLock();
    private StringBuilder builder;
    private Project project;
    private String group = "";

    @Override
    public String render(final Project project) {
        lock.lock();
        try {
            builder = new StringBuilder();
            this.project = project;

            appendHeader();
            project.getResources()
                .stream()
                .sorted(Comparator.comparing((Pair<String, ResourceMethod> p) -> p.getRight().getOriginalClass())
                        .thenComparing(p -> p.getLeft())
                        .thenComparing(p -> p.getRight().getMethod()))
                .forEach(this::appendResource);
            return builder.toString();
        } finally {
            lock.unlock();
        }
    }

    private void appendHeader() {
        builder.append("FORMAT: 1A\n\n");
    }

    private void appendResource(Pair<String, ResourceMethod> pair) {

        String path = pair.getLeft();
        ResourceMethod resourceMethod = pair.getRight();
        String simpleName = resourceMethod.getOriginalClass().replaceAll(".*/", "");

        if(!simpleName.equals(group)) {
            group = simpleName;
            builder.append("# Group ");
            builder.append(group);
            builder.append("\n\n");
        }

        builder.append("## ");
        builder.append(resourceMethod.getDescription());
        builder.append(" [");
        builder.append(resourceMethod.getMethod().toString());
        builder.append(" ");
        builder.append(project.getResources().getBasePath() + path);
        builder.append("]\n\n");

        // builder.append("+ Response 200\n\n");

    }

    @Override
    public String getName() {
        return "API Blueprint";
    }

    @Override
    public String getOutputFile(Project project) {
        return project.getName() + ".apib";
    }
}
