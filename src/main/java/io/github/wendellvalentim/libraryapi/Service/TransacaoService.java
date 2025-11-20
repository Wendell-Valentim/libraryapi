package io.github.wendellvalentim.libraryapi.Service;

import io.github.wendellvalentim.libraryapi.model.Autor;
import io.github.wendellvalentim.libraryapi.model.GeneroLivro;
import io.github.wendellvalentim.libraryapi.model.Livro;
import io.github.wendellvalentim.libraryapi.repository.AutorRepository;
import io.github.wendellvalentim.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class TransacaoService {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Transactional
    public void executar() {

        Autor autor = new Autor ();
        autor.setNome("TES FranciscO");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1978,8, 5));

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("272728-84874");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("TESTE LIVRO DO FRANCISCO");
        livro.setDataPublicacao(LocalDate.of( 1999,1,2));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("TESTE FranciscO")) {
            throw new RuntimeException("RollBack");
        }


    }
}
