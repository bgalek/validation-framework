package com.github.bgalek.utils.validationframework;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class CustomValidatorBuilder<T> implements ValidatorBuilder<T>, EnsuringValidatorBuilder<T>, ExceptionThrowingValidatorBuilder<T> {

    protected final Map<Predicate<T>, String> validations = new HashMap<>();
    private final ValidatorSupplier<T> validatorSupplier;
    private final Class<T> validatedType;

    public CustomValidatorBuilder(Class<T> validatedType, ValidatorSupplier<T> validatorSupplier) {
        this.validatedType = validatedType;
        this.validatorSupplier = validatorSupplier;
    }


    @Override
    public CustomValidatorBuilder<T> validation(Predicate<T> validation, String message) {
        validations.put(validation, message);
        return this;
    }

    @Override
    public Validator<T> ensure() {
        return validatorSupplier.apply(validations);
    }

    @Override
    public Validator<T> ensure(Function<String, RuntimeException> exceptionSupplier) {
        try {
            return validatorSupplier.apply(validations);
        } catch (Exception exception) {
            throw exceptionSupplier.apply(exception.getMessage());
        }
    }

    @Override
    public String toString() {
        return "CustomValidator for " + validatedType.getSimpleName();
    }
}
