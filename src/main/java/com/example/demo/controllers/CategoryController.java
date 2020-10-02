package com.example.demo.controllers;

import static com.example.demo.controllers.BookController.books;

import com.example.demo.errors.ResponseError;
import com.example.demo.helper.BookHelper;
import com.example.demo.helper.CategoryHelper;
import com.example.demo.models.Book;
import com.example.demo.models.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
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

@RequestMapping("/category")
@RestController
public class CategoryController {

    final ObjectMapper mapper = new ObjectMapper();
    public static List<Category> categories = new ArrayList<>();
    private static AtomicLong categoryId = new AtomicLong(4);

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        Category category = categories
            .stream()
            .filter(CategoryHelper.hasId(id))
            .findFirst()
            .orElse(null);
        if (category == null) {
            ResponseError responseError = new ResponseError("Not found", "Category does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
        Category category = categories
            .stream()
            .filter(CategoryHelper.hasName(name))
            .findFirst()
            .orElse(null);
        if (category == null) {
            ResponseError responseError = new ResponseError("Not found", "Category does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        Category result =
            categories
                .stream()
                .filter(CategoryHelper.hasName(category.getName()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            ResponseError responseError = new ResponseError("Bad request", "Category already exists");
            return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
        }
        Long id = categoryId.incrementAndGet();
        category.setId(id);
        categories.add(category);
        ObjectNode obj = mapper.createObjectNode();
        obj.put("id", id);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        Category result = categories
            .stream()
            .filter(CategoryHelper.hasId(id))
            .findFirst()
            .orElse(null);

        if (result == null) {
            ResponseError responseError = new ResponseError("Not found", "Category does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        result.setName(category.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {

        Category result = categories
            .stream()
            .filter(CategoryHelper.hasId(id))
            .findFirst()
            .orElse(null);
        if (result == null) {
            ResponseError responseError = new ResponseError("Not found", "Category does not exist");
            return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
        }
        books.
            removeIf(book -> book.getCategory().equals(result));
        categories.remove(result);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Category> getAll() {
        return categories;
    }

    @GetMapping("name/{name}/books")
    public ResponseEntity<?> booksByCategory(@PathVariable String name) {
        List<Book> result = books
            .stream()
            .filter(BookHelper.hasCategory(name))
            .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public static void initializeListOfCategories(List<Category> listOfCategories) {
        categories.addAll(listOfCategories);
    }
}
