package io.github.wendellvalentim.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
@Schema(name = "Usuario")
public record UsuarioDTO(
                        @NotBlank(message = "Campo obritaroio!")
                        @Schema(name = "login")
                        String login,
                        @NotBlank(message = "Campo obritaroio!")
                        @Schema(name = "senha")
                        String senha,
                        @Email(message = "Campo email invalido!")
                        @NotBlank(message = "Campo obritaroio!")
                        @Schema(name = "email")
                        String email,
                        @Schema(name = "roles")
                        List<String> roles) {
}
