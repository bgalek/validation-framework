package com.github.bgalek.utils.validationframework;

import java.util.Set;

public interface ValidationResult<T> {

    boolean isValid();

    T getValue();

    Set<String> processedValidations();
}
