package by.youngliqui.digitalLibrary.models;

import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Author should not be empty")
    private String author;

    @NotEmpty(message = "Year of publication should not be empty")
    private int year;
}
