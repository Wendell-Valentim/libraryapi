package io.github.wendellvalentim.libraryapi.repository;

import io.github.wendellvalentim.libraryapi.model.Autor;
import io.github.wendellvalentim.libraryapi.model.GeneroLivro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.github.wendellvalentim.libraryapi.model.Livro;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-272727");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Ciencias");
        livro.setDataPublicacao(LocalDate.of( 1990,10,5));

        Autor autor = autorRepository.findById(UUID.fromString("4631ced2-13a3-478f-bc24-e1ea5546e569")).orElse(null);

        livro.setAutor(autor);

        repository.save(livro);


    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of( 1980,1,2));

        Autor autor = new Autor ();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1951,1, 31));

        livro.setAutor(autor);

        repository.save(livro);


    }

    @Test
    void salvarAutoreLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of( 1980,1,2));

        Autor autor = new Autor ();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1951,1, 31));
        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);


    }

    @Test
    void atualizarAutorDoLivro(){

        var livroParaAtualizar = repository.findById(UUID.fromString("b24ad901-773b-41e9-b029-1c200e541d3a")).orElse(null);

        var maria = autorRepository.findById(UUID.fromString("d0d7265f-b206-4c23-b4be-1fdc647a5bb0")).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        repository.deleteById(UUID.fromString("bae37b24-d56c-4152-bd8a-6f08fde4c98e"));
    }

    @Test
    @Transactional
    void buscarLivroTeste() {
        Livro livro = repository.findById(UUID.fromString("")).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());

        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());

    }

    @Test
    void pesquisaPorTituloTeste() {
        List<Livro> lista = repository.findByTitulo("O ROUBO DA CASA ASSOMBRADA");
        lista.forEach(System.out::println);
    }
    @Test
    void pesquisaPorIsbnTeste() {
        List<Livro> lista = repository.findByIsbn("272728-84874");
        lista.forEach(System.out::println);
    }
    @Test
    void pesquisaPorTituloEPreco() {
        var preco = BigDecimal.valueOf(204.00);
        List<Livro> lista = repository.findByTituloAndPreco("O ROUBO DA CASA ASSOMBRADA", preco);
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivros(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarLivroDoAutor(){
        var resultado = repository.listarLivrosMisterioComPrecoAcimaDe();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGenero() {
        var listaGenero = repository.findByGenero(GeneroLivro.MISTERIO, "dataPublicacao");
        listaGenero.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParam() {
        var listaGenero = repository.findByGeneroPOsitionalParameters(GeneroLivro.MISTERIO, "dataPublicacao");
        listaGenero.forEach(System.out::println);
    }

    @Test
    void deletePorGneroTeste(){
        repository.deletarByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTeste(){
        repository.updateDataPublicacao(LocalDate.of(2000, 1, 1));
    }
}