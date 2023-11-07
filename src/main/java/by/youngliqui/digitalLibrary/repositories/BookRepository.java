package by.youngliqui.digitalLibrary.repositories;

import by.youngliqui.digitalLibrary.models.Book;
import by.youngliqui.digitalLibrary.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByOwnerId(int ownerId);

    @Modifying
    @Query("update Book b set b.owner.id=?2 where b.id=?1")
    void setPersonIdByBookId(int bookId, int personId);

    @Query("select p from Book b join Person p on b.owner.id=p.id where b.id=?1")
    Optional<Person> findBookOwnerByBookId(int bookId);

    @Modifying
    @Query("update Book b set b.owner.id=NULL where b.id=?1")
    void setPersonIdNullByBookId(int bookId);

    List<Book> findBooksByNameStartingWith(String startingWith);
}
