package io.github.wendellvalentim.libraryapi.controller.mappers;

import io.github.wendellvalentim.libraryapi.controller.dto.AutorDTO;
import io.github.wendellvalentim.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);
    AutorDTO toDTO(Autor autor);
}
