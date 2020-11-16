package pl.ps.creditapp.core.validation.reflection;

import pl.ps.creditapp.core.exception.ValidationException;

public interface ClassAnnotationProcessor {
    void process(Object object, Class aClass) throws IllegalAccessException, ValidationException;
}
