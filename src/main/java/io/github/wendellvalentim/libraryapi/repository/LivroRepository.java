package io.github.wendellvalentim.libraryapi.repository;

import io.github.wendellvalentim.libraryapi.model.Autor;
import io.github.wendellvalentim.libraryapi.model.GeneroLivro;
import io.github.wendellvalentim.libraryapi.model.Livro;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    // Query Method

    //select from livro where id_autor= id
    List<Livro> findByAutor(Autor autor);


    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);



    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    //select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL referencia as entidades e propriedades da entidade
    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
            select l.genero 
            from Livro l join l.autor a 
            where a.nacionalidade = 'Brasileiro' 
            order By l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    @Query("select l from Livro l where l.genero = 'MISTERIO' AND l.preco >= 200 order By l.titulo")
    List<Livro> listarLivrosMisterioComPrecoAcimaDe();


    @Query("select l.titulo, l.autor.nome from Livro l where l.preco = 204.00 order By l.autor.nome")
    List<Object[]> listarTitulosEAutoresPorPrecoFixo();

    @Query("SELECT l FROM Livro l WHERE l.titulo LIKE '%ROUBO%' AND l.autor.nacionalidade = 'Brasileira'")
    List<Livro> buscarLivrosComFiltroAvancado();

    //NAMED PARAMETERS -> PARAMETROS NOMEADOS
    @Query("select l from Livro l where l.genero = :genero order By :ordenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro,@Param("ordenacao") String ordenacao);

    //NPOSITIONAL PARAMETERS
    @Query("select l from Livro l where l.genero = ?1 order By ?2")
    List<Livro> findByGeneroPOsitionalParameters(GeneroLivro generoLivro, String ordenacao);

    @Query("delete from Livro where genero = ?1")
    @Modifying
    @Transactional
    void deletarByGenero(GeneroLivro genero);

    @Query("update Livro set dataPublicacao = ?1")
    @Modifying
    @Transactional
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn(
            String isbn
    );
}
