package io.github.wendellvalentim.libraryapi.validator;

import io.github.wendellvalentim.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.wendellvalentim.libraryapi.model.Autor;
import io.github.wendellvalentim.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        if(autorEncontrado.isEmpty()){
            return false;
        }

        Autor duplicadoo = autorEncontrado.get();

        if(autor.getId() != null && autor.getId().equals(duplicadoo.getId())) {
            return false;
        }

        return true;
    }

}
