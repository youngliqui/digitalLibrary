package by.youngliqui.digitalLibrary.dao;

import by.youngliqui.digitalLibrary.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setId(rs.getInt("person_id"));
        person.setName(rs.getString("name"));
        person.setBirthYear(rs.getInt("birth_year"));
        person.setEmail(rs.getString("email"));

        return person;
    }
}
