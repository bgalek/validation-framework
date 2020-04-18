package com.github.bgalek.utils.validationframework.hollywood.movies;

import java.util.Objects;

public class MovieCharacter {

    public final String value;

    public MovieCharacter(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCharacter character = (MovieCharacter) o;
        return Objects.equals(value, character.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
