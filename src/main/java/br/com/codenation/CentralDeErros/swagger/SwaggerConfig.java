package br.com.codenation.CentralDeErros.swagger;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${config.oauth2.accessTokenUri=http://localhost:8080/oauth/token}")
    private String accessTokenUri;

    @Bean
    public Docket apis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.codenation.CentralDeErros"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    private OAuth securityScheme() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
//        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
//        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = new ArrayList<>();
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
        grantTypes.add(passwordCredentialsGrant);

        return new OAuth("oauth2", authorizationScopeList, grantTypes);
    }

//    private SecurityScheme securityScheme() {
//        GrantType grantType = new AuthorizationCodeGrantBuilder()
//                .tokenEndpoint(new TokenEndpoint(accessTokenUri, "oauthtoken"))
//                .build();
//
//        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
//                .grantTypes(Arrays.asList(grantType))
//                .build();
//        return oauth;
//    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {

//        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
//        authorizationScopes[0] = new AuthorizationScope("read", "read all");
//        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
//        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        AuthorizationScope authorizationScope
                = new AuthorizationScope("ADMIN", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration
                ("client", "secret", "", "", "Bearer access token", ApiKeyVehicle.HEADER, HttpHeaders.AUTHORIZATION,"");
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Error Center API")
                .description("Application for recording and querying errors")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Github", "https://github.com/araujobarros/Error_Center", "araujo.barros@matematica.ufrj.br"))
                .build();

    }
}