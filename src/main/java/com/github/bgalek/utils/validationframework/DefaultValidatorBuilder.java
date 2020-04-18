package com.github.bgalek.utils.validationframework;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class DefaultValidatorBuilder<T> implements ValidatorBuilder<T>, EnsuringValidatorBuilder<T>, ExceptionThrowingValidatorBuilder<T>, SummeringValidatorBuilder<T> {

    private final Map<Predicate<T>, String> validations = new HashMap<>();
    private final Class<T> validatedType;

    public DefaultValidatorBuilder(Class<T> validatedType) {
        this.validatedType = validatedType;
    }

    @Override
    public DefaultValidatorBuilder<T> validation(Predicate<T> validation, String message) {
        validations.put(validation, message);
        return this;
    }

    @Override
    public Validator<T> ensure() {
        return new DefaultValidator<>(validations);
    }

    @Override
    public Validator<T> ensure(Function<String, RuntimeException> exceptionSupplier) {
        return new ExceptionThrowingValidator<>(validations, exceptionSupplier);
    }

    @Override
    public SummaryValidator<T> summary() {
        return new DefaultSummaryValidator<>(validations);
    }

    @Override
    public String toString() {
        return "Validator for " + validatedType.getSimpleName();
    }
}