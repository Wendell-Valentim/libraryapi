package io.github.wendellvalentim.libraryapi.validator;

import io.github.wendellvalentim.libraryapi.exceptions.CampoInvalidoException;
import io.github.wendellvalentim.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.wendellvalentim.libraryapi.model.Livro;
import io.github.wendellvalentim.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static  final int ANO_EXGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar (Livro livro) {
        if(existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("ISBN ja Cadastrado!");
        }

        if(precoObrigatorio(livro)) {
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020, campo preço obrigatorio!");
        }
    }

    private boolean precoObrigatorio(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXGENCIA_PRECO;
    }

    public boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null) {
            return livroEncontrado.isPresent();
        }

       return livroEncontrado
               .map(Livro::getId)
               .stream()
               .anyMatch(id -> !id.equals(livro.getId()));
    }
}
