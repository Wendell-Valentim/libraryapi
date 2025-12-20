package io.github.wendellvalentim.libraryapi.controller;

import io.github.wendellvalentim.libraryapi.Service.UsuarioService;
import io.github.wendellvalentim.libraryapi.controller.dto.UsuarioDTO;
import io.github.wendellvalentim.libraryapi.controller.mappers.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuario")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Salvar", description = "Cadastrar um novo Usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso.")
    })
    public void salvar(@RequestBody @Valid  UsuarioDTO dto) {
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);

    }

}
