package io.github.wendellvalentim.libraryapi.controller.dto;

import io.github.wendellvalentim.libraryapi.model.Autor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 2, max = 100, message = "Numero de caracteres excedeu o limite!")
        @Schema(name = "nome")
        String nome,

        @NotNull(message = "Campo obrigatorio")
        @Past(message = "Não pode ser uma data futura")
        @Schema(name = "dataNascimento")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 2,max = 50, message = "Numero de caracteres excedeu o limite!")
        @Schema(name = "nacionalidade")
        String nacionalidade) {


}
