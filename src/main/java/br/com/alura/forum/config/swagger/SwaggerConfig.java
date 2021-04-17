package br.com.alura.forum.config.swagger;

import br.com.alura.forum.modelo.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
//localhost:8080/swagger-ui.html
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
                .paths(PathSelectors.ant("/**")) // como nao temos nenhum endpoint restrita, vai gerar documentacao de tudo
                .build()
                .ignoredParameterTypes(Usuario.class) // ignorar qualquer endpoint com parametro relacionado ao Usuario.class, para nao mostrar dados como senha do usuario
                .globalOperationParameters(Arrays.asList(  // parametros que eu quero que o swager apresente em todos endpoints, embora estamos passando uma lista, foi passado apenas 1 parametro
                                        new ParameterBuilder()
                                        .name("Authorization")
                                        .description("Header para Token JWT")
                                        .modelRef(new ModelRef("string")) // tipo do parametro
                                        .parameterType("header")    // no nosso caso é um header e nao um parametro no corpo da requisicao
                                        .required(false) // parametro opcional pq tem endereços que nao é necessario informar o token
                                        .build()));
    }

}
