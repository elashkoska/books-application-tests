package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Author {

    private Long id;
    private String name;
    private String surname;

}
