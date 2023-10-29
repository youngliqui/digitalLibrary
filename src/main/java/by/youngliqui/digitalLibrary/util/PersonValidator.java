package by.youngliqui.digitalLibrary.util;

import by.youngliqui.digitalLibrary.models.Person;
import by.youngliqui.digitalLibrary.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Optional<Person> showPersonByEmail = peopleService.findOneByEmail(person.getEmail());
        Optional<Person> showPersonByName = peopleService.findOneByName(person.getName());

        if (showPersonByEmail.isPresent()) {
            if (showPersonByEmail.get().getId() != person.getId()) {
                errors.rejectValue("email", "", "This email is already taken");
            }
        }

        if (showPersonByName.isPresent()) {
            if (showPersonByName.get().getId() != person.getId()) {
                errors.rejectValue("name", "", "This full name is already taken");
            }
        }
    }
}
