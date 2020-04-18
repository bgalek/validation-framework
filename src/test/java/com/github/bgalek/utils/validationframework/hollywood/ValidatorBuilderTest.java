package com.github.bgalek.utils.validationframework.hollywood;

import com.github.bgalek.utils.validationframework.Validator;
import com.github.bgalek.utils.validationframework.hollywood.movies.MovieCharacter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ValidatorFactory")
class ValidatorBuilderTest {

    @Test
    @DisplayName("should throw exception if error during configuration occurs")
    void test() {
        //given
        Validator<MovieCharacter> validator = Validator.of(MovieCharacter.class)
                .validation(character -> List.of().get(100).equals("out of bounds!"), "ups!")
                .ensure();

        //expect
        assertThrows(IndexOutOfBoundsException.class, () -> validator.validate(new MovieCharacter("test")));
    }
}