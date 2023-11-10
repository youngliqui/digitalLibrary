package by.youngliqui.digitalLibrary.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "Author should not be empty")
    private String author;

    @Column(name = "year_of_production")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date yearOfProduction;

    @Column(name = "date_of_capture")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCapture;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Transient
    private boolean isOverdue;

    public Book() {
    }

    public Book(String name, String author, Date yearOfProduction, Person owner) {
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
        this.owner = owner;
    }
    public Book(String name, String author, Date yearOfProduction) {
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
    }

    public Book(String name, String author, Date yearOfProduction, Date dateOfCapture) {
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
        this.dateOfCapture = dateOfCapture;
    }

    public Book(String name, String author, Date yearOfProduction, Date dateOfCapture, Person owner) {
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
        this.dateOfCapture = dateOfCapture;
        this.owner = owner;
    }

    public Date getDateOfCapture() {
        return dateOfCapture;
    }

    public void setDateOfCapture(Date dateOfCapture) {
        this.dateOfCapture = dateOfCapture;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Date yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(yearOfProduction, book.yearOfProduction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, yearOfProduction);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearOfProduction=" + yearOfProduction +
                ", owner=" + owner +
                '}';
    }
}
