package com.example.demo.helper;


import com.example.demo.models.Book;
import java.util.function.Predicate;

public class BookHelper {

    public static Predicate<Book> hasId(Long id) {
        return book -> book.getId().equals(id);
    }

    public static Predicate<Book> hasIsbn(Long isbn) {
        return book -> book.getIsbn().equals(isbn);
    }

    public static Predicate<Book> hasAuthor(String author) {
        return book -> book.getAuthors().contains(author);
    }

    public static Predicate<Book> hasCategory(String name) {
        return book -> book.getCategory().getName().equals(name);
    }
}
