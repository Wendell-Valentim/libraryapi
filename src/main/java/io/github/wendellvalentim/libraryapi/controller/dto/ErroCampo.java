package io.github.wendellvalentim.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DetalheErroValidacao")
public record ErroCampo(String campo, String erro) {
}
