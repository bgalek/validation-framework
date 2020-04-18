package com.github.bgalek.utils.validationframework;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ValidationSummary<T> implements ValidationResults<T> {
    private final Map<Boolean, Set<ValidationResult<T>>> results;

    ValidationSummary(Map<Boolean, Set<ValidationResult<T>>> results) {
        this.results = results;
    }

    static <T> ValidationSummary<T> of(Map<Boolean, Set<ValidationResult<T>>> results) {
        return new ValidationSummary<>(results);
    }

    @Override
    public Set<ValidationResult<T>> getPassedValidationResults() {
        return new HashSet<>(results.getOrDefault(Boolean.TRUE, Set.of()));
    }

    @Override
    public Set<ValidationResult<T>> getFailedValidationResults() {
        return new HashSet<>(results.getOrDefault(Boolean.FALSE, Set.of()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationSummary<?> that = (ValidationSummary<?>) o;
        return Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return results != null ? results.hashCode() : 0;
    }
}
