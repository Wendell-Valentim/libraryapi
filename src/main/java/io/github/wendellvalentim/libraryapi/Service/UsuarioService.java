package io.github.wendellvalentim.libraryapi.Service;

import io.github.wendellvalentim.libraryapi.model.Usuario;
import io.github.wendellvalentim.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void salvar (Usuario usuario) {
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return repository.findByLogin(login);
    }

    public Usuario obterPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public String gerarSenhaAleatoria() {
        String senhaUUID = UUID.randomUUID().toString();

        String senhaLimpa = senhaUUID.replace("-", "");

        return senhaLimpa.substring(0, 16);
    }
}
