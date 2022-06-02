package br.com.redpillrh.casedevjava.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PersonIdentifierConflictException extends RuntimeException {

    public static final String IDENTIFIER_CONFLICT_MESSAGE = "O Identificador informado já está cadastrado";
    private HttpStatus httpStatusCode;

    public PersonIdentifierConflictException() {
        super(IDENTIFIER_CONFLICT_MESSAGE);
        httpStatusCode = HttpStatus.CONFLICT;
    }
}
