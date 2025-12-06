package io.github.wendellvalentim.libraryapi.Service;

import io.github.wendellvalentim.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.wendellvalentim.libraryapi.model.GeneroLivro;
import io.github.wendellvalentim.libraryapi.model.Livro;
import io.github.wendellvalentim.libraryapi.model.Usuario;
import io.github.wendellvalentim.libraryapi.repository.LivroRepository;
import io.github.wendellvalentim.libraryapi.repository.specs.LivroSpecs;
import io.github.wendellvalentim.libraryapi.security.SecurityService;
import io.github.wendellvalentim.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.wendellvalentim.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        Usuario usuarioEncontrado = securityService.obterUsuarioLogado();
        livro.setUsuario(usuarioEncontrado);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);

    }

    public void atualizar (Livro livro) {
        if (livro.getId() == null) {
            throw new OperacaoNaoPermitidaException("Para atualizar o livro precisa estar cadastrado!");
        }
        validator.validar(livro);
        repository.save(livro);
    }

    public void deletar (Livro livro) {
        repository.delete(livro);

    }

    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer anoPublicacao,
                                Integer pagina,
                                Integer tamanhoPagina) {

        //select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction() );

        if (isbn != null ){
            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }

        if( genero != null) {
            specs = specs.and(generoEqual(genero));
        }

        if(anoPublicacao != null) {
            specs = specs.and(dataPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina,tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }


}
