package io.github.wendellvalentim.libraryapi.controller.dto;

import io.github.wendellvalentim.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 2, max = 100, message = "Numero de caracteres excedeu o limite!")
        String nome,

        @NotNull(message = "Campo obrigatorio")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 2,max = 50, message = "Numero de caracteres excedeu o limite!")
        String nacionalidade) {


}
