package br.com.redpillrh.casedevjava.model;

import br.com.redpillrh.casedevjava.enums.IdentifierType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    @Schema(name = "id", required = true, example = "123")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    int id;

    @Schema(name = "name", required = true, example = "Jane Doe")
    @Column(nullable = false)
    @NotEmpty(message = "Campo nome é obrigatório")
    String name;

    @Schema(name = "identifier", required = true, example = "11122233344")
    @Column(nullable = false, unique = true)
    String identifier;

    @Schema(name = "identifierType", required = true, example = "CPF")
    @Column(nullable = false)
    IdentifierType identifierType;

}
