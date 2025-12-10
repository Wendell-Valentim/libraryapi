package io.github.wendellvalentim.libraryapi.security;

import io.github.wendellvalentim.libraryapi.Service.UsuarioService;
import io.github.wendellvalentim.libraryapi.model.Usuario;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UsuarioService service;

//    public Usuario obterUsuarioLogado () {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
//        String login = userDetails.getUsername();
//        return service.obterPorLogin(login);
//    }

    public Usuario obterUsuarioLogado () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof  CustomAuthentication customAuth) {
            return customAuth.getUsuario();
        }
        return null;
    }
}
