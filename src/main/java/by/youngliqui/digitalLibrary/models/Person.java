package by.youngliqui.digitalLibrary.models;

import jakarta.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "Your full name should be in this format: Last name First name Patronymic")
    private String name;

    @Min(value = 1900, message = "Year of birth should be valid")
    @Max(value = 2023, message = "Year of birth should be valid")
    private int birthYear;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;


    public Person() {
    }

    public Person(int id, String name, int birthYear, String email) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
