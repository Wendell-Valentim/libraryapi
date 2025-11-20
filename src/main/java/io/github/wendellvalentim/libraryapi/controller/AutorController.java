package io.github.wendellvalentim.libraryapi.controller;


import io.github.wendellvalentim.libraryapi.Service.AutorService;
import io.github.wendellvalentim.libraryapi.controller.dto.AutorDTO;
import io.github.wendellvalentim.libraryapi.controller.dto.ErroResposta;
import io.github.wendellvalentim.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.wendellvalentim.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.wendellvalentim.libraryapi.model.Autor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
// http://localhost:8080/autores
public class AutorController {


    private final AutorService service;

    public AutorController(AutorService autorService) {
        this.service = autorService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar (@RequestBody @Valid AutorDTO autor) {
        try{
        Autor autorEntity = autor.mapearParaAutor();
        service.salvar(autorEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(autorEntity.getId())
                .toUri();
        return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes (@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarAutor(@PathVariable("id") String id) {
        try{
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if ((autorOptional.isEmpty())){
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }
    catch (OperacaoNaoPermitidaException e) {
        var erroReposta = ErroResposta.respostaPadrao(e.getMessage());
        return ResponseEntity.status(erroReposta.status()).body(erroReposta);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar (@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
       try {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);


        if (autorOptional.isEmpty()) {
               return ResponseEntity.notFound().build();
        }

           var autor = autorOptional.get();
           autor.setNome(dto.nome());
           autor.setNacionalidade(dto.nacionalidade());
           autor.setDataNascimento(dto.dataNascimento());

           service.atualizar(autor);

        return ResponseEntity.noContent().build();
       } catch (RegistroDuplicadoException e) {
           var erroDTO = ErroResposta.conflito(e.getMessage());
           return ResponseEntity.status(erroDTO.status()).body(erroDTO);

       }
    }
}
