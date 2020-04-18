package com.github.bgalek.utils.validationframework;

public interface SummaryValidator<T> {
    ValidationSummary<T> validateAll(T subject);
}
