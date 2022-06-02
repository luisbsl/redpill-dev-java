package br.com.redpillrh.casedevjava.validator;

import br.com.redpillrh.casedevjava.exception.PersonIdentifierConflictException;
import br.com.redpillrh.casedevjava.exception.PersonIdentifierSizeException;
import br.com.redpillrh.casedevjava.repository.PersonRepository;
import br.com.redpillrh.casedevjava.utils.PersonCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonIdentifierValidatorTests {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonIdentifierValidator personIdentifierValidator;

    @BeforeEach
    void cleanup() {
        personRepository.deleteAll();
    }

    @Test
    void testPersonIdentifierValidatorDuplicationException() {

        personRepository.save(PersonCreator.duplicatedPerson());

        PersonIdentifierConflictException thrown = Assertions
                .assertThrows(PersonIdentifierConflictException.class, () -> {
                    personIdentifierValidator.validate(PersonCreator.duplicatedPerson().getIdentifier());
                }, PersonIdentifierConflictException.IDENTIFIER_CONFLICT_MESSAGE);

        assertEquals(PersonIdentifierConflictException.IDENTIFIER_CONFLICT_MESSAGE, thrown.getMessage());
    }

    @Test
    void testPersonIdentifierSizeException() {

        PersonIdentifierSizeException thrown = Assertions
                .assertThrows(PersonIdentifierSizeException.class, () -> {
                    personIdentifierValidator.validate("123");
                }, PersonIdentifierSizeException.IDENTIFIER_SIZE_MESSAGE);

        assertEquals(PersonIdentifierSizeException.IDENTIFIER_SIZE_MESSAGE, thrown.getMessage());
    }

    @Test
    void testPersonIdentifierValidatorSuccess() {
        personIdentifierValidator.validate("11122233344");
    }



}
