package com.github.bgalek.utils.validationframework.hollywood;

import com.github.bgalek.utils.validationframework.ValidationResult;
import com.github.bgalek.utils.validationframework.Validator;
import com.github.bgalek.utils.validationframework.hollywood.movies.MovieCharacter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("StandardValidator")
class DefaultValidatorTest {

    private final Validator<MovieCharacter> characterValidator = Validator.of(MovieCharacter.class)
            .validation(character -> character.value.length() > 0, "name needs to has to be at least 1 character long")
            .validation(character -> Character.isUpperCase(character.value.charAt(0)), "name needs to start with uppercase letter")
            .validation(character -> character.value.length() < 50, "name needs to has to be shorter than 50 characters")
            .ensure();

    @Test
    @DisplayName("should pass validations")
    void youShallPass() {
        //given
        MovieCharacter character = new MovieCharacter("Donna Pinciotti");

        //when
        ValidationResult<MovieCharacter> validationResult = characterValidator.validate(character);

        //then
        assertTrue(validationResult.isValid());
        assertEquals(character, validationResult.getValue());
        assertEquals(
                Set.of("name needs to start with uppercase letter", "name needs to has to be at least 1 character long", "name needs to has to be shorter than 50 characters"),
                validationResult.processedValidations()
        );
    }

    @Test
    @DisplayName("should not pass validation")
    void youShallNotPass() {
        //given
        MovieCharacter character = new MovieCharacter("eric foreman");

        //when
        ValidationResult<MovieCharacter> validationResult = characterValidator.validate(character);

        //then
        assertFalse(validationResult.isValid());
        assertEquals(validationResult.processedValidations(), Set.of("name needs to start with uppercase letter"));
        assertEquals(character, validationResult.getValue());
    }
}