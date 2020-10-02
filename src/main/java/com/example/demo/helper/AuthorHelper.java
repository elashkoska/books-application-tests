package com.example.demo.helper;

import com.example.demo.models.Author;
import java.util.function.Predicate;

public class AuthorHelper {

    public static Predicate<Author> hasId(Long id) {
        return author -> author.getId().equals(id);
    }

    public static Predicate<Author> hasName(String name) {
        return author -> author.getName().equals(name);
    }

    public static Predicate<Author> hasSurname(String surname) {
        return author -> author.getSurname().equals(surname);
    }
}
