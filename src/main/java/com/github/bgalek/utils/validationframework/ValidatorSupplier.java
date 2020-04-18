package com.github.bgalek.utils.validationframework;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface ValidatorSupplier<T> extends Function<Map<Predicate<T>, String>, Validator<T>> {
}
