package br.com.redpillrh.casedevjava.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PersonIdentifierSizeException extends RuntimeException {

    public static final String IDENTIFIER_SIZE_MESSAGE = "O campo Identificador deve possuir 11 dígitos para CPF ou 14 dígitos para CNPJ";
    private HttpStatus httpStatusCode;

    public PersonIdentifierSizeException() {
        super(IDENTIFIER_SIZE_MESSAGE);
        httpStatusCode = HttpStatus.BAD_REQUEST;
    }
}
