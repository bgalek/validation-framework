package com.github.bgalek.utils.validationframework;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class ExceptionThrowingValidator<T> implements Validator<T> {
    private final Function<String, RuntimeException> exceptionFunction;
    private final DefaultValidator<T> defaultValidator;

    ExceptionThrowingValidator(Map<Predicate<T>, String> validations, Function<String, RuntimeException> exceptionFunction) {
        this.exceptionFunction = exceptionFunction;
        this.defaultValidator = new DefaultValidator<>(validations);
    }

    @Override
    public ValidationResult<T> validate(T subject) {
        ValidationResult<T> validationResult = defaultValidator.validate(subject);
        if (validationResult.isValid()) {
            return new PositiveValidationResult<>(subject, validationResult.processedValidations());
        }
        throw exceptionFunction.apply(String.join("", validationResult.processedValidations()));
    }
}
