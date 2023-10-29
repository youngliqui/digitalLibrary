package by.youngliqui.digitalLibrary.repositories;

import by.youngliqui.digitalLibrary.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
    public Optional<Person> findOneByName(String name);

    public Optional<Person> findOneByEmail(String email);


}
