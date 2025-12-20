package io.github.wendellvalentim.libraryapi.controller.dto;

import io.github.wendellvalentim.libraryapi.model.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Schema(name = "DadosNovoLivro")
public record CadastroLivroDTO(
                               @NotBlank(message = "Campo obrigatorio!")
                               @ISBN
                               String isbn,
                               @NotBlank(message = "Campo obrigatorio!")
                               String titulo,
                               @NotNull
                               @Past(message = "Data futura não é permitida!")
                               LocalDate dataPublicacao,
                               GeneroLivro genero,
                               BigDecimal preco,
                               @NotNull
                               UUID idAutor
) {
}
