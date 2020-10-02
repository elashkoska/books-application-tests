package com.example.demo.helper;

import com.example.demo.models.Category;
import java.util.function.Predicate;

public class CategoryHelper {


    public static Predicate<Category> hasId(Long id) {
        return category ->  category.getId().equals(id);
    }

    public static Predicate<Category> hasName(String name) {
        return category ->  category.getName().equals(name);
    }
}
