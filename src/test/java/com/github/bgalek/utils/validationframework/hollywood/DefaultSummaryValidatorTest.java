package com.github.bgalek.utils.validationframework.hollywood;

import com.github.bgalek.utils.validationframework.SummaryValidator;
import com.github.bgalek.utils.validationframework.ValidationSummary;
import com.github.bgalek.utils.validationframework.Validator;
import com.github.bgalek.utils.validationframework.hollywood.movies.MovieCharacter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DefaultSummaryValidator")
class DefaultSummaryValidatorTest {

    @Test
    @DisplayName("should return validation summary")
    void test() {
        //given
        SummaryValidator<MovieCharacter> characterValidator = Validator.of(MovieCharacter.class)
                .validation(character -> character.value.startsWith("A"), "only names starting with A allowed")
                .validation(character -> character.value.split(" ").length == 2, "has to contain name and surname")
                .validation(character -> character.value.endsWith("Z"), "only names ending with Z allowed")
                .summary();

        MovieCharacter character = new MovieCharacter("Morty Smith");

        //when
        ValidationSummary<MovieCharacter> nameValidationSummary = characterValidator.validateAll(character);

        //then
        assertEquals(1, nameValidationSummary.getPassedValidationResults().size());
        assertEquals(2, nameValidationSummary.getFailedValidationResults().size());
        assertEquals(
                Set.of("has to contain name and surname"),
                nameValidationSummary.getPassedValidationResults().stream().flatMap(validationResult -> validationResult.processedValidations().stream()).collect(toSet())
        );
        assertEquals(
                Set.of("only names starting with A allowed", "only names ending with Z allowed"),
                nameValidationSummary.getFailedValidationResults().stream().flatMap(validationResult -> validationResult.processedValidations().stream()).collect(toSet())
        );
    }
}