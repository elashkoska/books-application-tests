package com.example.demo.controllers;


import static com.example.demo.controllers.BookController.books;

import com.example.demo.errors.ResponseError;
import com.example.demo.helper.AuthorHelper;
import com.example.demo.helper.BookHelper;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/author")
@RestController
public class AuthorController {

    public static List<Author> authors = new ArrayList<>();
    private static AtomicLong authorId = new AtomicLong(11);

    private final ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/name/{name}")
    public ResponseEntity<?> getAuthorByName(@PathVariable String name) {

        Author author = authors.stream()
            .filter(AuthorHelper.hasName(name))
            .findFirst()
            .orElse(null);
        if (author == null) {
            ResponseError responseError = new ResponseError("Not found", "Author does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<?> getAuthorBySurname(@PathVariable String surname) {
        Author author = authors.stream()
            .filter(AuthorHelper.hasSurname(surname))
            .findFirst()
            .orElse(null);
        if (author == null) {
            ResponseError responseError = new ResponseError("Not found", "Author does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable Long id) {

        Author author = authors.stream()
            .filter(AuthorHelper.hasId(id))
            .findFirst()
            .orElse(null);
        if (author == null) {
            ResponseError responseError = new ResponseError("Not found", "Author does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {

        if (containsAuthor(author)) {
            ResponseError responseError = new ResponseError("Bad request", "Author already exists");
            return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
        }
        Long id = authorId.incrementAndGet();
        author.setId(id);
        authors.add(author);
        ObjectNode node = objectMapper.createObjectNode();
        node.put("id", id);
        return new ResponseEntity<>(node, HttpStatus.CREATED);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {

        Author author = authors
            .stream()
            .filter(AuthorHelper.hasId(id))
            .findFirst()
            .orElse(null);

        if (author == null) {
            ResponseError responseError = new ResponseError("Not found", "Author does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        authors.remove(author);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateAuthor(@RequestBody Author author, @PathVariable Long id) {

        Author result =
            authors
                .stream()
                .filter(AuthorHelper.hasId(id))
                .findFirst()
                .orElse(null);

        if (result == null) {
            ResponseError responseError = new ResponseError("Not found", "Author does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        result.setName(author.getName());
        result.setSurname(author.getSurname());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Author> getAll() {
        return authors;
    }

    @GetMapping("/id/{id}/books")
    public ResponseEntity<?> booksByAuthor(@PathVariable Long id) {
        Author author = authors
            .stream()
            .filter(AuthorHelper.hasId(id))
            .findFirst()
            .orElse(null);

        if (author == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }

        String fullName = author.getName() + " " + author.getSurname();
        List<Book> result = books
            .stream()
            .filter(BookHelper.hasAuthor(fullName))
            .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private boolean containsAuthor(Author author) {
        Author result = authors
            .stream()
            .filter(AuthorHelper.hasName(author.getName())
                .and(AuthorHelper.hasSurname(author.getSurname())))
            .findAny()
            .orElse(null);

        return (result != null) ? true : false;
    }

    public static void initializeListOfAuthors(List<Author> listOfAuthors) {
        authors.addAll(listOfAuthors);
     }

}
