package com.github.bgalek.utils.validationframework.hollywood;

import com.github.bgalek.utils.validationframework.ValidationResult;
import com.github.bgalek.utils.validationframework.Validator;
import com.github.bgalek.utils.validationframework.hollywood.movies.MovieCharacter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ExceptionThrowingValidator")
class ExceptionThrowingValidatorTest {

    @Test
    @DisplayName("should pass validation without throwing exception")
    void youShallPass() {
        //given
        Validator<MovieCharacter> characterValidator = Validator.of(MovieCharacter.class)
                .validation(character -> character.value.equals("Bojack Horseman"), "only Horses allowed")
                .ensure(IllegalArgumentException::new);
        MovieCharacter character = new MovieCharacter("Bojack Horseman");

        //when:
        ValidationResult<MovieCharacter> validationResult = characterValidator.validate(character);

        //expect
        assertTrue(validationResult.isValid());
        assertEquals(character, validationResult.getValue());
        assertEquals(Set.of("only Horses allowed"), validationResult.processedValidations());
    }

    @Test
    @DisplayName("should not pass validation and throw chosen exception")
    void youShallNotPass() {
        //given
        Validator<MovieCharacter> characterValidator = Validator.of(MovieCharacter.class)
                .validation(character -> character.value.equals("Bojack Horseman"), "only Horses allowed")
                .ensure(NotHorseException::new);
        MovieCharacter ericForeman = new MovieCharacter("Diane Nguyen");

        //expect
        assertThrows(NotHorseException.class, () -> characterValidator.validate(ericForeman));
    }

    private static class NotHorseException extends IllegalArgumentException {
        public NotHorseException(String s) {
            super(s);
        }
    }
}