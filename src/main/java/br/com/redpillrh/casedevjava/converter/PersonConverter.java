package br.com.redpillrh.casedevjava.converter;

import br.com.redpillrh.casedevjava.dto.PersonDto;
import br.com.redpillrh.casedevjava.enums.IdentifierType;
import br.com.redpillrh.casedevjava.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public final class PersonConverter {

    @Autowired
    private ObjectMapper objectMapper;

    public static Person fromDto(PersonDto dto) {
        final String identifier = dto.getIdentifier();
        return Person
                .builder()
                .identifier(identifier)
                .name(dto.getName())
                .identifierType(identifier.length() == 11 ? IdentifierType.CPF : IdentifierType.CNPJ)
                .build();
    }

    public static PersonDto fromEntity(Person person) {
        return new ModelMapper().map(person, PersonDto.class);
    }

}
