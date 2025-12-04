package io.github.wendellvalentim.libraryapi.controller.mappers;

import io.github.wendellvalentim.libraryapi.controller.dto.UsuarioDTO;
import io.github.wendellvalentim.libraryapi.model.Usuario;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
