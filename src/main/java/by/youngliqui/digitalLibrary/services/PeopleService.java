package by.youngliqui.digitalLibrary.services;

import by.youngliqui.digitalLibrary.models.Book;
import by.youngliqui.digitalLibrary.models.Person;
import by.youngliqui.digitalLibrary.repositories.BookRepository;
import by.youngliqui.digitalLibrary.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BookRepository bookRepository;

    private final BookService bookService;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BookRepository bookRepository, BookService bookService) {
        this.peopleRepository = peopleRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        return person.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());

        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);

        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> findOneByEmail(String email) {
        return peopleRepository.findOneByEmail(email);
    }

    public Optional<Person> findOneByName(String name) {
        return peopleRepository.findOneByName(name);
    }

    public List<Book> findBooksByPersonId(int id) {
        List<Book> books = bookRepository.findBooksByOwnerId(id);

        books.forEach(book -> bookService.checkOverdueBook(book.getId()));

        return books;
    }
}
