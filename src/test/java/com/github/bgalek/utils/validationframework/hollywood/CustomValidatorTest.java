package com.github.bgalek.utils.validationframework.hollywood;

import com.github.bgalek.utils.validationframework.PositiveValidationResult;
import com.github.bgalek.utils.validationframework.ValidationResult;
import com.github.bgalek.utils.validationframework.Validator;
import com.github.bgalek.utils.validationframework.hollywood.movies.MovieCharacter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("CustomValidator")
class CustomValidatorTest {

    private static final StringBuilder console = new StringBuilder();

    private final Validator<MovieCharacter> customValidator = Validator.of(MovieCharacter.class, SystemOutValidator::new)
            .validation(character -> character.value.length() > 0, "name needs to has to be at least 1 character long")
            .validation(character -> character.value.length() < 50, "name needs to has to be shorter than 50 characters")
            .ensure();

    @Test
    @DisplayName("should be able to use custom validator")
    void youShallPass() {
        //given
        MovieCharacter character = new MovieCharacter("Scrooge McDuck");

        //when
        ValidationResult<MovieCharacter> validationResult = customValidator.validate(character);

        //then
        assertTrue(validationResult.isValid());
        assertTrue(console.toString().contains("'name needs to has to be at least 1 character long' passed!"));
        assertTrue(console.toString().contains("'name needs to has to be shorter than 50 characters' passed!"));
    }

    static class SystemOutValidator<T> implements Validator<T> {

        private final Map<Predicate<T>, String> validations;

        public SystemOutValidator(Map<Predicate<T>, String> validations) {
            this.validations = validations;
        }

        @Override
        public ValidationResult<T> validate(T subject) {
            validations.forEach((key, value) -> {
                if (key.test(subject)) console.append(String.format("'%s' passed!", value));
                else console.append(String.format("'%s' failed!", value));
                console.append(System.getProperty("line.separator"));
            });
            return new PositiveValidationResult<>(subject, new HashSet<>(validations.values()));
        }
    }
}