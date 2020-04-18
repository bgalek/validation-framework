package com.github.bgalek.utils.validationframework;

public interface Validator<T> {
    static <T> DefaultValidatorBuilder<T> of(Class<T> validatedType) {
        return new DefaultValidatorBuilder<>(validatedType);
    }

    static <T> CustomValidatorBuilder<T> of(Class<T> clazz, ValidatorSupplier<T> validatorSupplier) {
        return new CustomValidatorBuilder<>(clazz, validatorSupplier);
    }

    ValidationResult<T> validate(T subject);
}
