package by.youngliqui.digitalLibrary.services;

import by.youngliqui.digitalLibrary.models.Book;
import by.youngliqui.digitalLibrary.models.Person;
import by.youngliqui.digitalLibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private static final long TIME_OF_DELAY = 864_000_000;
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

    public List<Book> findAll(String nameOfSortedField) {
        return bookRepository.findAll(Sort.by(nameOfSortedField));
    }

    public List<Book> findAll(int page, int itemsPerPage, String nameOfSortedField) {
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by(nameOfSortedField))).getContent();
    }

    public Book findOne(int id) {
        Book book =  bookRepository.findById(id).orElse(null);
        checkOverdueBook(id);
        return book;
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = bookRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
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
        bookRepository.findById(bookId).ifPresent(book -> book.setDateOfCapture(new Date()));
    }

    @Transactional
    public void release(int id) {
        bookRepository.setPersonIdNullByBookId(id);
        bookRepository.findById(id).ifPresent(book -> book.setDateOfCapture(null));
    }

    public List<Book> findBooksByNameStartingWith(String startingWith) {
        return bookRepository.findBooksByNameStartingWith(startingWith);
    }

    public boolean checkOverdueBook(int id) {
        Book book = bookRepository.findById(id).get();
        if (book.getOwner() == null) {
            book.setOverdue(false);
            return false;
        }
        Date currentTime = new Date();
        if (currentTime.getTime() - book.getDateOfCapture().getTime() > TIME_OF_DELAY) {
            book.setOverdue(true);
            return true;
        } else {
            book.setOverdue(false);
            return false;
        }
    }
}
