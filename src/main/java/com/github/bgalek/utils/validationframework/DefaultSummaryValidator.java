package com.github.bgalek.utils.validationframework;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DefaultSummaryValidator<T> implements SummaryValidator<T> {

    private final Map<Predicate<T>, String> validations;

    DefaultSummaryValidator(Map<Predicate<T>, String> validations) {
        this.validations = validations;
    }

    @Override
    public ValidationSummary<T> validateAll(T subject) {
        Map<Boolean, Set<ValidationResult<T>>> results = validations.entrySet()
                .stream()
                .map(entry -> {
                    if (entry.getKey().test(subject)) return new PositiveValidationResult<>(subject, Set.of(entry.getValue()));
                    return new NegativeValidationResult<>(subject, Set.of(entry.getValue()));
                })
                .collect(Collectors.groupingBy(ValidationResult::isValid, Collectors.toSet()));
        return ValidationSummary.of(results);
    }
}
