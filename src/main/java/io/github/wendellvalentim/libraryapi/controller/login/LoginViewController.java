package io.github.wendellvalentim.libraryapi.controller.login;

import io.github.wendellvalentim.libraryapi.security.CustomAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

@Tag(name = "Segurança")
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    @GetMapping("/me")
    @ResponseBody
    @Operation(summary = "Dados", description = "Dados do usuario logado.")
    public String getAuthenticatedUser(Authentication authentication) {
        if(authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUsuario());
        }
        return "Ola " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    @Operation(summary = "Dados", description = "Codigo de autorização do usuario logado.")
    public String getAuthorizationCode(@RequestParam("code") String code) {
        return "Seu authorization code:" + code;
    }
}
