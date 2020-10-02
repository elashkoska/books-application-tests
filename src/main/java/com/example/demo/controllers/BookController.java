package com.example.demo.controllers;


import com.example.demo.errors.ResponseError;
import com.example.demo.helper.BookHelper;
import com.example.demo.models.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
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

@RequestMapping("/book")
@RestController
public class BookController {


    public static List<Book> books = new ArrayList<>();
    private static AtomicLong bookId = new AtomicLong(6);
    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping("id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Book book = books.stream()
            .filter(BookHelper.hasId(id))
            .findFirst()
            .orElse(null);

        if (book == null) {
            ResponseError responseError = new ResponseError("Not found", "Book does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("isbn/{isbn}")
    public ResponseEntity<?> getByIsbn(@PathVariable Long isbn) {
        Book book = books.stream()
            .filter(BookHelper.hasIsbn(isbn))
            .findFirst()
            .orElse(null);

        if (book == null) {
            ResponseError responseError = new ResponseError("Not found", "Book does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        Book b = books.stream()
            .filter(BookHelper.hasIsbn(book.getIsbn()))
            .findFirst()
            .orElse(null);
        if (b != null) {
            ResponseError responseError = new ResponseError("Bad request", "Book already exists");
            return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
        }
        Long id = bookId.incrementAndGet();
        book.setId(id);
        books.add(book);
        ObjectNode obj = mapper.createObjectNode();
        obj.put("id", id);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateBook(@RequestBody Book book, @PathVariable Long id) {
        Book result = books
            .stream()
            .filter(BookHelper.hasId(id))
            .findFirst()
            .orElse(null);

        if (result == null) {
            ResponseError responseError = new ResponseError("Not found", "Book does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        result.setAuthors(book.getAuthors());
        result.setDescription(book.getDescription());
        result.setPages(book.getPages());
        result.setPrice(book.getPrice());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {

        Book book = books.stream()
            .filter(BookHelper.hasId(id))
            .findFirst()
            .orElse(null);
        if (book == null) {
            ResponseError responseError = new ResponseError("Not found", "Book does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        books.remove(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Book> getAll() {
        return books;
    }

    public static void initializeListOfBooks(List<Book> listOfBooks) {
        books.addAll(listOfBooks);
    }
}
