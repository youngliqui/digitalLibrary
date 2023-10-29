package by.youngliqui.digitalLibrary.repositories;

import by.youngliqui.digitalLibrary.models.Book;
import by.youngliqui.digitalLibrary.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    public List<Book> findBooksByOwnerId(int personId);

    @Modifying
    @Query("update Book b set b.owner.id=?1 where b.id=?2")
    void setPersonIdByBookId(int bookId, int personId);

    @Query("select Person from Book b join Person p on b.owner.id=p.id where b.id=?")
    public Optional<Person> findBookOwnerByBookId(int bookId);

    @Modifying
    @Query("update Book b set b.owner.id=NULL where b.id=?")
    void setPersonIdNullByBookId(int bookId);
}
