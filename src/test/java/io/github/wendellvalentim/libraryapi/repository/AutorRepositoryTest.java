package io.github.wendellvalentim.libraryapi.repository;

import io.github.wendellvalentim.libraryapi.model.Autor;
import io.github.wendellvalentim.libraryapi.model.GeneroLivro;
import io.github.wendellvalentim.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor ();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1951,1, 31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo:" + autorSalvo);


    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("18c3b555-4839-4b57-9eab-cbf6a5d4082f");
        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));
            repository.save(autorEncontrado);
        }

    }
    public void deletePorIdTest() {
        var id = UUID.fromString("18c3b555-4839-4b57-9eab-cbf6a5d4082f");
        repository.deleteById(id);
    }

    public void listarTest() {
        List<Autor> list = repository.findAll();
        list.forEach(System.out::println);
    }

    public void countTest() {
        System.out.println("Contagem de autores:" + repository.count());
    }


    @Test
    void salvarAutorComLivrosTeste(){
        Autor autor = new Autor ();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1978,8, 5));

        Livro livro = new Livro();
        livro.setIsbn("272728-84874");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("O ROUBO DA CASA ASSOMBRADA");
        livro.setDataPublicacao(LocalDate.of( 1999,1,2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("272728-84874");
        livro2.setPreco(BigDecimal.valueOf(204));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("O ROUBO DA CASA ASSOMBRADA");
        livro2.setDataPublicacao(LocalDate.of( 1999,1,2));
        livro2.setAutor(autor);




        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosAutor() {
        var autor = repository.findById(UUID.fromString("bc433169-63cc-420b-9323-0e3fe28c03d8")).get();

        List<Livro> listaLivros = livroRepository.findByAutor(autor);
        autor.setLivros(listaLivros);

        autor.getLivros().forEach(System.out::println);
        
    }
}
