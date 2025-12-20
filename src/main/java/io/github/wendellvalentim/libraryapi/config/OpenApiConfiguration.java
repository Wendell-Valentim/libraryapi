package io.github.wendellvalentim.libraryapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Library API",
                version = "v1",
                contact = @Contact(
                        name = "Wendell da Silva",
                        email = "wendellldasiilva8@gmail.com",
                        url = "libraryapi.com"
                )
        ),
        security =  {
                //solicitar a segurança por meio do bearer
                @SecurityRequirement(name = "bearerAuth")
        }
)
//definindo como é a bearer
@SecurityScheme(name = "bearerAuth",
                //http: vamos colocar o token e o envio é em HTTP
                type = SecuritySchemeType.HTTP,
                //formato do token
                bearerFormat = "JWT",
                scheme = "bearer",
                in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}
