package io.github.wendellvalentim.libraryapi.controller.mappers;

import io.github.wendellvalentim.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.wendellvalentim.libraryapi.controller.dto.ResultadoLivroPesquisaDTO;
import io.github.wendellvalentim.libraryapi.model.Livro;
import io.github.wendellvalentim.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity (CadastroLivroDTO dto);


    public abstract ResultadoLivroPesquisaDTO toDTO(Livro livro);

    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract void updateEntityFromDto(CadastroLivroDTO dto, @MappingTarget Livro livro);

}
