package com.example.demo;

import static com.example.demo.controllers.AuthorController.initializeListOfAuthors;
import static com.example.demo.controllers.BookController.initializeListOfBooks;
import static com.example.demo.controllers.CategoryController.initializeListOfCategories;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Category;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {

            List<Author> authors = Arrays.asList(
                new Author(1l, "Elisabeth", "Freeman"),
                new Author(2l, "Kathy", "Sierra"),
                new Author(3l, "Craig", "Walls"),
                new Author(4l, "Mike", "Benshoof"),
                new Author(5l, "Vlad", "Mihalcea"),
                new Author(6l, "Josip", "Maras"),
                new Author(7l, "Bear", "Bibeault"),
                new Author(8l, "John", "Resig"),
                new Author(9l, "Robert", "Sedgewick"),
                new Author(10l, "Steven", "Skiena"),
                new Author(11l, "Mert", "Caliskan")
            );

            List<Category> categories = Arrays.asList(
                new Category(1l, "Data Structures and Algorithms"),
                new Category(2l, "Web Development"),
                new Category(3l, "Databases"),
                new Category(4l, "Machine Learning")
            );

            List<Book> books = Arrays.asList(
                new Book("Head  First Design Patterns", "Design patterns in Java",
                    10d, 500, Arrays.asList("Elisabeth Freeman", "Kathy Sierra"), 111l, 1l, categories.get(1)),
                new Book("Spring in Action", "Spring from beginner to guru",
                    15d, 450, Arrays.asList("Craig Walls"), 222l, 2l, categories.get(1)),
                new Book("MySQL Tuning  SQL  Queries", "Query optimization",
                    23d, 250, Arrays.asList("Mike Benshoof", "Vlad Mihalcea"), 333l, 3l,
                    categories.get(2)),
                new Book("Javascript Ninja 2nd edition", "Javascript core concepts",
                    27d, 230, Arrays.asList("Josip Maras", "Bear Bibeault", "John Resig"), 444l, 4l, categories.get(1)),
                new Book("Algorithms & Data Structures", "Algorithms for beginners",
                    30d, 230, Arrays.asList("Robert Sedgewick", "Steven Skiena"), 555l, 5l, categories.get(0)),

                new Book("Deep Learning", "Machine Learning for novice",
                    111d, 280, Arrays.asList(" Aaron Courville"), 666l, 6l, categories.get(3))
            );

            initializeListOfAuthors(authors);
            initializeListOfCategories(categories);
            initializeListOfBooks(books);
        };
    }

}
