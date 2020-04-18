package com.github.bgalek.utils.validationframework;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class CustomSummaryValidatorBuilder<T> implements ValidatorBuilder<T>, SummeringValidatorBuilder<T> {

    protected final Map<Predicate<T>, String> validations = new HashMap<>();
    private final SummaryValidatorSupplier<T> summaryValidatorSupplier;
    private final Class<T> validatedType;

    public CustomSummaryValidatorBuilder(Class<T> validatedType, SummaryValidatorSupplier<T> summaryValidatorSupplier) {
        this.validatedType = validatedType;
        this.summaryValidatorSupplier = summaryValidatorSupplier;
    }

    @Override
    public ValidatorBuilder<T> validation(Predicate<T> validation, String message) {
        validations.put(validation, message);
        return this;
    }

    @Override
    public String toString() {
        return "CustomValidator for " + validatedType.getSimpleName();
    }

    @Override
    public SummaryValidator<T> summary() {
        return summaryValidatorSupplier.apply(validations);
    }
}
