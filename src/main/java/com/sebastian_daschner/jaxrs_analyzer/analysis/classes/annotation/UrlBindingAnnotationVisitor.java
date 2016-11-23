package com.sebastian_daschner.jaxrs_analyzer.analysis.classes.annotation;

import com.sebastian_daschner.jaxrs_analyzer.model.Tech;
import com.sebastian_daschner.jaxrs_analyzer.model.results.ClassResult;
import com.sebastian_daschner.jaxrs_analyzer.model.results.MethodResult;

/**
 * @author Sebastian Daschner
 */
public class UrlBindingAnnotationVisitor extends ClassAndMethodAnnotationVisitor {

    public UrlBindingAnnotationVisitor(final ClassResult classResult) {
        super(classResult);
    }

    @Override
    protected void visitValue(final String value, final ClassResult classResult) {
        classResult.setTech(Tech.STRIPES);
        classResult.setResourcePath("/");
        classResult.setUrlBinding(value);
    }

    @Override
    protected void visitValue(final String value, final MethodResult methodResult) {
        // should not happen
        throw new IllegalStateException("UrlBinding should not be on methods");
    }
}
