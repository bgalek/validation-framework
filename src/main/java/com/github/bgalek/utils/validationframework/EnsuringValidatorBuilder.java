package com.github.bgalek.utils.validationframework;

public interface EnsuringValidatorBuilder<T> {
    Validator<T> ensure();
}
