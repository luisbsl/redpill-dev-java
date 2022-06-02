package br.com.redpillrh.casedevjava.utils;

import br.com.redpillrh.casedevjava.enums.IdentifierType;
import br.com.redpillrh.casedevjava.model.Person;

public final class PersonCreator {

    public static Person duplicatedPerson() {
        return  Person
                .builder()
                .identifier("11122233344")
                .name("Jane Doe")
                .identifierType(IdentifierType.CPF)
                .build();
    }

}
