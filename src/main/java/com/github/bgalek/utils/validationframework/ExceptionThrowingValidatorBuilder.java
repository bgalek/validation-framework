package com.github.bgalek.utils.validationframework;

import java.util.function.Function;

public interface ExceptionThrowingValidatorBuilder<T> {
    Validator<T> ensure(Function<String, RuntimeException> exceptionSupplier);
}
