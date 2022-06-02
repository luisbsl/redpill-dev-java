package br.com.redpillrh.casedevjava.validator;

import br.com.redpillrh.casedevjava.exception.PersonIdentifierConflictException;
import br.com.redpillrh.casedevjava.exception.PersonIdentifierSizeException;
import br.com.redpillrh.casedevjava.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@AllArgsConstructor
public final class PersonIdentifierValidator {

    private PersonRepository personRepository;

    public void validate(String identifier) {
        isDuplicated().andThen(isValidSize()).accept(identifier);
    }

    private Consumer<String> isDuplicated() {
        return identifier -> {
            if (personRepository.findByIdentifier(identifier).isPresent()) {
                throw new PersonIdentifierConflictException();
            };
        };
    }

    private Consumer<String> isValidSize() {
        return identifier -> {
            int identifierSize = identifier.length();
            if(identifierSize != 11 && identifierSize != 14) {
                throw new PersonIdentifierSizeException();
            }
        };
    }

}
