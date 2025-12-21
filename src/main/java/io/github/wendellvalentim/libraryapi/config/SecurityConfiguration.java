package io.github.wendellvalentim.libraryapi.config;

import io.github.wendellvalentim.libraryapi.Service.UsuarioService;
import io.github.wendellvalentim.libraryapi.model.Usuario;
//import io.github.wendellvalentim.libraryapi.security.CustomUserDetailService;
import io.github.wendellvalentim.libraryapi.security.JwtCustomAuthenticationFilter;
import io.github.wendellvalentim.libraryapi.security.LoginSocialSucessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//permitir colocar verificações no controller
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http,
                                                    LoginSocialSucessHandler sucessHandler,
                                                    JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
        return http
                //dasbilitar para que outras aplicações como Angular ou React possam ter comunicação com a aplicação/API
                //configurador HTTP ABSTRACT, por este comando desabilito o csrf
                .csrf(AbstractHttpConfigurer::disable)
                //formulario padrão, metodo não faz "nada"
               .formLogin(configurer -> {
                   configurer.loginPage("/login").permitAll();
                })
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login").permitAll();

                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();

                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .loginPage("/login")
                            .successHandler(sucessHandler);
                })
                .oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

    //IGNORAR VERIFICAÇÃO DO SECURITY PARA ESTAS URLS
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/v2/api-docs/**",
                         "/v3/api-docs/**",
                         "/swagger-resources/**",
                         "/swagger-ui.html",
                         "/swagger-ui/**",
                         "/webjars/**",
                         //é bom colocar para autenticação
                         "/actuator/**"
        );
    }

    //CONFIGURA O PREFIXO ROLE
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults () {
        return new GrantedAuthorityDefaults("");
    }

    //CONFIGURA, NO TOKEN JWT, O PREFIXO SCOPE
    @Bean
    public JwtAuthenticationConverter authenticationConverter () {
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }



}
