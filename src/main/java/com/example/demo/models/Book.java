package com.example.demo.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Book {

    private String title;
    private String description;
    private Double price;
    private int pages;
    private List<String>  authors;
    private Long isbn;
    private Long id;
    private Category category;


}
