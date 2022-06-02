package br.com.redpillrh.casedevjava.rest;

import br.com.redpillrh.casedevjava.dto.PersonDto;
import br.com.redpillrh.casedevjava.model.Person;
import br.com.redpillrh.casedevjava.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
@Tag(name = "person", description = "Person endpoints")
public class PersonRestController {

    private PersonService personService;

    @Operation(summary = "Save Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person was created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "409", description = "Person identifier already exists",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content ) })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> save(@Valid @RequestBody PersonDto personDto) {
        return new ResponseEntity<Person>(personService.save(personDto), HttpStatus.CREATED);
    }

}
