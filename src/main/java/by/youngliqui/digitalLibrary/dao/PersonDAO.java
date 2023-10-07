package by.youngliqui.digitalLibrary.dao;

import by.youngliqui.digitalLibrary.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE email=?",
                new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, birthYear, email) VALUES (?, ?, ?)",
                person.getName(), person.getBirthYear(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, birthYear=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getBirthYear(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

}
