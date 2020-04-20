# Java validation framework
> Simple and extensible validator pattern implementation with sensible defaults

![Build](https://github.com/bgalek/validation-framework/workflows/Java%20CI%20with%20Gradle/badge.svg)
![Codecov](https://img.shields.io/codecov/c/github/bgalek/validation-framework.svg?style=flat-square)
![GitHub Release Date](https://img.shields.io/github/release-date/bgalek/validation-framework.svg?style=flat-square)
![Scrutinizer code quality](https://img.shields.io/scrutinizer/g/bgalek/validation-framework.svg?style=flat-square)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bgalek_validationframework&metric=alert_status)](https://sonarcloud.io/dashboard?id=bgalek_validationframework)

## Why?
How many times You had to implement Validator Pattern? Or maybe how many times You wish you did?
This java library provides some easy and extensible interfaces to introduce consistent way of validation through your application.

## Usage
Add library dependency:
```groovy
compile "com.github.bgalek.utils:validationframework:1.0.0"
```

Let's assume a very simple class to validate:
```java
public class MovieCharacter {
    public final String value;
}
```

Use provided implementations:

### DefaultValidator
Validations are processed respecting declaration order.
Will return a `PositiveValidationResult` only when all validations are successful, otherwise, it stops at first fail encountered and returns: `NegativeValidationResult`

```java
Validator<MovieCharacter> characterValidator = Validator.of(MovieCharacter.class)
        .validation(movieStar -> movieStar.value.length() > 0, "name needs to has to be at least 1 character long")
        .validation(movieStar -> Character.isUpperCase(movieStar.value.charAt(0)), "name needs to start with uppercase letter")
        .validation(movieStar -> movieStar.value.length() < 50, "name needs to has to be shorter than 50 characters")
        .ensure();
```

### DefaultValidator with exception instead of validation result
Validations are processed respecting declaration order.
Will return a `PositiveValidationResult` only when all validations are successful, otherwise, it stops at first fail encountered and throws provided exception.

```java
Validator<MovieCharacter> characterValidator = Validator.of(MovieCharacter.class)
        .validation(movieStar -> movieStar.value.length() > 0, "name needs to has to be at least 1 character long")
        .validation(movieStar -> Character.isUpperCase(movieStar.value.charAt(0)), "name needs to start with uppercase letter")
        .validation(movieStar -> movieStar.value.length() < 50, "name needs to has to be shorter than 50 characters")
        .ensure(IllegalArgumentException::new);
```

### SummaryValidator
Validations are processed respecting declaration order.
Will return a `ValidationSummary` containing passed and failed validations.

```java
SummaryValidator<MovieCharacter> characterValidator = Validator.of(MovieCharacter.class)
        .validation(movieStar -> movieStar.value.startsWith("A"), "only names starting with A allowed")
        .validation(movieStar -> movieStar.value.split(" ").length == 2, "has to contain name and surname")
        .validation(movieStar -> movieStar.value.endsWith("Z"), "only names ending with Z allowed")
        .summary();
```

## Extending
You can implement your own validators
```java
class SystemOutValidator<T> implements Validator<T> {

        private final Map<Predicate<T>, String> validations;

        public SystemOutValidator(Map<Predicate<T>, String> validations) {
            this.validations = validations;
        }

        @Override
        public ValidationResult<T> validate(T subject) {
            validations.forEach((key, value) -> System.out.println("processing: " + value));
            return new PositiveValidationResult<>(subject, new HashSet<>(validations.values()));
        }
    }
```

and use them like this:

```java
Validator<MovieCharacter> customValidator = Validator.of(MovieCharacter.class, SystemOutValidator::new)
        .validation(movieStar -> movieStar.value.length() > 0, "name needs to has to be at least 1 character long")
        .validation(movieStar -> movieStar.value.length() < 50, "name needs to has to be shorter than 50 characters")
        .ensure();
```

Check out unit tests for more examples!
