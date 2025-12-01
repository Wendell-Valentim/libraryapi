package io.github.wendellvalentim.libraryapi.controller.dto;

import io.github.wendellvalentim.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoLivroPesquisaDTO(UUID id,
                                        String isbn,
                                        String titulo,
                                        LocalDate dataPublicacao,
                                        GeneroLivro genero,
                                        BigDecimal preco,
                                        AutorDTO autor
                                        ) {
}
