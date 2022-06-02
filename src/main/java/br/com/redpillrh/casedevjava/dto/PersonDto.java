package br.com.redpillrh.casedevjava.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDto {

    @Schema(required = true, example = "Jane Doe")
    @NotEmpty(message = "Campo nome é obrigatório")
    String name;

    @Schema(required = true, example = "11122233344")
    @NotEmpty(message = "Campo identificador é obrigatório")
    @Pattern(regexp = "^\\d+$", message = "Campo identificador é apenas numérico (CPF/CNPJ)")
    String identifier;

}
