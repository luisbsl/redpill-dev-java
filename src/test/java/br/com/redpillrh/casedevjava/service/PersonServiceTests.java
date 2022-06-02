package br.com.redpillrh.casedevjava.service;

import br.com.redpillrh.casedevjava.converter.PersonConverter;
import br.com.redpillrh.casedevjava.dto.PersonDto;
import br.com.redpillrh.casedevjava.enums.IdentifierType;
import br.com.redpillrh.casedevjava.exception.PersonIdentifierConflictException;
import br.com.redpillrh.casedevjava.exception.PersonIdentifierSizeException;
import br.com.redpillrh.casedevjava.model.Person;
import br.com.redpillrh.casedevjava.repository.PersonRepository;
import br.com.redpillrh.casedevjava.utils.PersonCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonServiceTests {

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    void cleanup() {
        personRepository.deleteAll();
    }

    @Test
    void testPersonServiceSaveIdentifierConflictException() {
        personRepository.save(PersonCreator.duplicatedPerson());

        PersonIdentifierConflictException thrown = Assertions
                .assertThrows(PersonIdentifierConflictException.class, () -> {
                    personService.save(PersonConverter.fromEntity(PersonCreator.duplicatedPerson()));
                }, PersonIdentifierConflictException.IDENTIFIER_CONFLICT_MESSAGE);

        assertEquals(PersonIdentifierConflictException.IDENTIFIER_CONFLICT_MESSAGE, thrown.getMessage());
    }

    @Test
    void testPersonServiceSaveIdentifierSizeException() {

        PersonDto invalidIdentifierSizeDto = PersonConverter.fromEntity(PersonCreator.duplicatedPerson());
        invalidIdentifierSizeDto.setIdentifier("123");

        PersonIdentifierSizeException thrown = Assertions
                .assertThrows(PersonIdentifierSizeException.class, () -> {
                    personService.save(invalidIdentifierSizeDto);
                }, PersonIdentifierSizeException.IDENTIFIER_SIZE_MESSAGE);

        assertEquals(PersonIdentifierSizeException.IDENTIFIER_SIZE_MESSAGE, thrown.getMessage());
    }

    @Test
    void testPersonServiceSaveSuccess_thenReturn_CreatedPersonWithIdentifierTypeCPF() {

        PersonDto validDto = PersonConverter.fromEntity(PersonCreator.duplicatedPerson());
        validDto.setIdentifier("66655544411");

        Person createdPerson = personService.save(validDto);

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertEquals(IdentifierType.CPF, createdPerson.getIdentifierType());
    }

    @Test
    void testPersonServiceSaveSuccess_thenReturn_CreatedPersonWithIdentifierTypeCNPJ() {

        PersonDto validDto = PersonConverter.fromEntity(PersonCreator.duplicatedPerson());
        validDto.setIdentifier("11122233344455");

        Person createdPerson = personService.save(validDto);

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertEquals(IdentifierType.CNPJ, createdPerson.getIdentifierType());
    }

}
