package io.github.wendellvalentim.libraryapi.controller;

import io.github.wendellvalentim.libraryapi.Service.LivroService;
import io.github.wendellvalentim.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.wendellvalentim.libraryapi.controller.dto.ErroResposta;
import io.github.wendellvalentim.libraryapi.controller.dto.ResultadoLivroPesquisaDTO;
import io.github.wendellvalentim.libraryapi.controller.mappers.LivroMapper;
import io.github.wendellvalentim.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.wendellvalentim.libraryapi.model.GeneroLivro;
import io.github.wendellvalentim.libraryapi.model.Livro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
@Tag(name = "Livros")
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;


    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo Livro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Livro ja cadastrado.")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {

        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(location).build();


    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary ="Atualizar", description = "Atualizar Livro por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado."),
            @ApiResponse(responseCode = "409", description = "Livro ja Cadastrado")
    })
    public ResponseEntity<Object> atualizar (@PathVariable("id") @Valid String id ,@RequestBody CadastroLivroDTO dto){
        return service.obterPorId(UUID.fromString(id))
                .map(livroExistente -> {
                    mapper.updateEntityFromDto(dto, livroExistente);
                    service.atualizar(livroExistente);
                    return ResponseEntity.noContent().build();
                }).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Obter detalhes", description = "Obter detalhes do Livro por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<ResultadoLivroPesquisaDTO> obterDetalhes(
            @PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Deletar", description = "Deletar livro por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    })
    public ResponseEntity<Object> deletarLivro(@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Pesquisar livro por parametro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso.")
    })
    public ResponseEntity<Page<ResultadoLivroPesquisaDTO>> pesquisa(@RequestParam(value = "isbn", required = false)
                                                                    String isbn,
                                                                    @RequestParam(value = "titulo", required = false)
                                                                    String titulo,
                                                                    @RequestParam(value = "nome-autor", required = false)
                                                                    String nomeAutor,
                                                                    @RequestParam(value = "genero", required = false)
                                                                    GeneroLivro genero,
                                                                    @RequestParam(value = "ano-publicacao", required = false)
                                                                    Integer anoPublicacao,
                                                                    @RequestParam(value = "pagina", defaultValue = "0")
                                                                    Integer pagina,
                                                                    @RequestParam(value="tamanho-pagina", defaultValue = "10")
                                                                    Integer tamanhoPagina

    ) {

        Page<Livro> paginaResultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<ResultadoLivroPesquisaDTO> resultado = paginaResultado.map(mapper::toDTO);


        return ResponseEntity.ok(resultado);

    }

}
