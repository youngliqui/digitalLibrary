package by.youngliqui.digitalLibrary.services;

import by.youngliqui.digitalLibrary.models.Book;
import by.youngliqui.digitalLibrary.models.Person;
import by.youngliqui.digitalLibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAll(int page, int itemsPerPage) {
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Person> findBookOwnerByBookId(int bookId) {
        return bookRepository.findBookOwnerByBookId(bookId);
    }

    @Transactional
    public void appoint(int bookId, Person selectedPerson) {
        bookRepository.setPersonIdByBookId(bookId, selectedPerson.getId());
    }

    @Transactional
    public void release(int id) {
        bookRepository.setPersonIdNullByBookId(id);
    }
}
