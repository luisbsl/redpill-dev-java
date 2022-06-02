package br.com.redpillrh.casedevjava.config;

import br.com.redpillrh.casedevjava.exception.PersonIdentifierConflictException;
import br.com.redpillrh.casedevjava.exception.PersonIdentifierSizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream().map(f -> f.getDefaultMessage()).map(String::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorBody(errors));
    }

    @ExceptionHandler(PersonIdentifierConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<Object> handlePersonIdentifierConflictException(PersonIdentifierConflictException ex) {
        return ResponseEntity.status(ex.getHttpStatusCode()).body(createErrorBody(List.of(ex.getMessage())));
    }

    @ExceptionHandler(PersonIdentifierSizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handlePersonIdentifierConflictException(PersonIdentifierSizeException ex) {
        return ResponseEntity.status(ex.getHttpStatusCode()).body(createErrorBody(List.of(ex.getMessage())));
    }

    private  Map<String, Object> createErrorBody(List errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errors", errors);
        return body;
    }
}


