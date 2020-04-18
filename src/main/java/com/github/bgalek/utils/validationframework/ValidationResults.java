package com.github.bgalek.utils.validationframework;

import java.util.Set;

public interface ValidationResults<T> {
    Set<ValidationResult<T>> getPassedValidationResults();
    Set<ValidationResult<T>> getFailedValidationResults();
}
