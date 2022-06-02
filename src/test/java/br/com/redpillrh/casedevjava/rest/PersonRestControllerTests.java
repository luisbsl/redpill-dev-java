package br.com.redpillrh.casedevjava.rest;


import br.com.redpillrh.casedevjava.converter.PersonConverter;
import br.com.redpillrh.casedevjava.dto.PersonDto;
import br.com.redpillrh.casedevjava.exception.PersonIdentifierConflictException;
import br.com.redpillrh.casedevjava.exception.PersonIdentifierSizeException;
import br.com.redpillrh.casedevjava.model.Person;
import br.com.redpillrh.casedevjava.repository.PersonRepository;
import br.com.redpillrh.casedevjava.utils.PersonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void cleanup() {
        personRepository.deleteAll();
    }

    @Test
    void testPersonRestControllerSave_thenReturn_requiredNameField_HTTPStatus400() throws Exception {
        PersonDto validDto = PersonConverter.fromEntity(Person
                .builder()
                .identifier("00011122233")
                .build());

        mockMvc.perform(post("/persons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("Campo nome é obrigatório")));
    }

    @Test
    void testPersonRestControllerSave_thenReturn_requiredIdentifierField_HTTPStatus400() throws Exception {
        PersonDto validDto = PersonConverter.fromEntity(Person
                .builder()
                .name("Jane Dow")
                .build());

        mockMvc.perform(post("/persons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("Campo identificador é obrigatório")));
    }

    @Test
    void testPersonRestControllerSave_thenReturn_requiredAllFields_HTTPStatus400() throws Exception {
        PersonDto validDto = PersonConverter.fromEntity(Person
                .builder()
                .build());

        mockMvc.perform(post("/persons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItem("Campo nome é obrigatório")))
                .andExpect(jsonPath("$.errors", hasItem("Campo identificador é obrigatório")));
    }

    @Test
    void testPersonRestControllerSave_thenReturn_IsNotNumericIdentifierField_HTTPStatus400() throws Exception {
        PersonDto validDto = PersonConverter.fromEntity(Person
                .builder()
                .name("Jane Dow")
                .identifier("CPF")
                .build());

        mockMvc.perform(post("/persons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("Campo identificador é apenas numérico (CPF/CNPJ)")));
    }

    @Test
    void testPersonRestControllerSave_thenReturn_createdPersonWithIdentifierTypeCPF() throws Exception {
        PersonDto validDto = PersonConverter.fromEntity(Person
                .builder()
                .identifier("00011122233")
                .name("Jane Doe")
                .build());

        mockMvc.perform(post("/persons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").isNotEmpty())
                        .andExpect(jsonPath("$.identifierType").value("CPF")) ;
    }

    @Test
    void testPersonRestControllerSave_thenReturn_createdPersonWithIdentifierTypeCNPJ() throws Exception {
        PersonDto validDto = PersonConverter.fromEntity(Person
                .builder()
                .identifier("99922211188899")
                .name("Jane Doe")
                .build());

        mockMvc.perform(post("/persons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.identifierType").value("CNPJ")) ;
    }

    @Test
    void testPersonRestControllerSave_thenReturn_HTTPStatus409() throws Exception {

        personRepository.save(PersonCreator.duplicatedPerson());

        mockMvc.perform(post("/persons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(PersonConverter.fromEntity(PersonCreator.duplicatedPerson()))))
                        .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.errors").isArray())
                        .andExpect(jsonPath("$.errors", hasSize(1)))
                        .andExpect(jsonPath("$.errors", hasItem(PersonIdentifierConflictException.IDENTIFIER_CONFLICT_MESSAGE)));
    }

    @Test
    void testPersonRestControllerSave_thenReturn_HTTPStatus400() throws Exception {

        PersonDto invalidIdentifierSizeDto = PersonConverter.fromEntity(PersonCreator.duplicatedPerson());
        invalidIdentifierSizeDto.setIdentifier("123");

        mockMvc.perform(post("/persons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidIdentifierSizeDto)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors").isArray())
                        .andExpect(jsonPath("$.errors", hasSize(1)))
                        .andExpect(jsonPath("$.errors", hasItem(PersonIdentifierSizeException.IDENTIFIER_SIZE_MESSAGE)));
    }

}
