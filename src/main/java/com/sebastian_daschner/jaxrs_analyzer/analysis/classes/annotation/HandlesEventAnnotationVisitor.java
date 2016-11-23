package com.sebastian_daschner.jaxrs_analyzer.analysis.classes.annotation;

import com.sebastian_daschner.jaxrs_analyzer.LogProvider;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.HttpMethod;
import com.sebastian_daschner.jaxrs_analyzer.model.results.ClassResult;
import com.sebastian_daschner.jaxrs_analyzer.model.results.MethodResult;

/**
 * @author Sebastian Daschner
 */
public class HandlesEventAnnotationVisitor extends ClassAndMethodAnnotationVisitor {

    public HandlesEventAnnotationVisitor(final MethodResult methodResult) {
        super(methodResult);
    }

    @Override
    protected void visitValue(final String value, final ClassResult classResult) {
        throw new IllegalStateException("HandlesEvent should not be on classes");
    }

    @Override
    protected void visitValue(final String value, final MethodResult methodResult) {
        methodResult.setHttpMethod(HttpMethod.GET);
        methodResult.setPath(methodResult.getParentResource().getUrlBinding().replace("{$event}", value));
    }
}
