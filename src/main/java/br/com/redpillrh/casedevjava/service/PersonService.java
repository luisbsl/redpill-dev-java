package br.com.redpillrh.casedevjava.service;

import br.com.redpillrh.casedevjava.converter.PersonConverter;
import br.com.redpillrh.casedevjava.dto.PersonDto;
import br.com.redpillrh.casedevjava.model.Person;
import br.com.redpillrh.casedevjava.repository.PersonRepository;
import br.com.redpillrh.casedevjava.validator.PersonIdentifierValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;
    private PersonIdentifierValidator personIdentifierValidator;

    public Person save(PersonDto personDto) {

        personIdentifierValidator.validate(personDto.getIdentifier());
        return personRepository.save(PersonConverter.fromDto(personDto));

    }

}
