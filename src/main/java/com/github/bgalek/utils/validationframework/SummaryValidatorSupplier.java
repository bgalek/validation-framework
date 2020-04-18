package com.github.bgalek.utils.validationframework;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface SummaryValidatorSupplier<T> extends Function<Map<Predicate<T>, String>, SummaryValidator<T>> {
}
