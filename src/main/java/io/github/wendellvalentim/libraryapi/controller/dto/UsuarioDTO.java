package io.github.wendellvalentim.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioDTO(
                        @NotBlank(message = "Campo obritaroio!")
                        String login,
                        @NotBlank(message = "Campo obritaroio!")
                        String senha,
                        @Email(message = "Campo email invalido!")
                        @NotBlank(message = "Campo obritaroio!")
                        String email,
                        List<String> roles) {
}
