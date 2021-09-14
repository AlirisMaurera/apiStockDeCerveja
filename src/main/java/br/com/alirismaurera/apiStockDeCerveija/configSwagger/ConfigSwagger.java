package br.com.alirismaurera.apiStockDeCerveija.configSwagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static springfox.documentation.builders.RequestHandlerSelectors.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ConfigSwagger {

    private static final String BASE_PACOTE = "br.com.alirismaurera.apiStockDeCerveija.controller";
    private static final String API_TITULO = "Stock De Cerveija";
    private static final String API_DESCRICAO = "Api para o gerenciamento de um stock de cerveijas";
    private static final String  NOME_CONTATO= "Aliris Maurera";
    private static final String  CONTA_GITHUB= "https://github.com/AlirisMaurera";
    private static final String  CONTA_EMAIL= "alirismaurera@gmail.com";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(basePackage(BASE_PACOTE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo(){
        return new ApiInfoBuilder()
                .title(API_TITULO)
                .description(API_DESCRICAO)
                .version("1.0.0")
                .contact(new Contact(NOME_CONTATO, CONTA_GITHUB, CONTA_EMAIL))
                .build();

    }
}



