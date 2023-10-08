package by.youngliqui.digitalLibrary.util;

import by.youngliqui.digitalLibrary.dao.PersonDAO;
import by.youngliqui.digitalLibrary.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Optional<Person> showPerson = personDAO.show(person.getEmail());

        if (showPerson.isPresent()) {
            if (showPerson.get().getId() != person.getId()) {
                errors.rejectValue("email", "", "This email is already taken");
            }
        }
    }
}
