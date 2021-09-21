package br.com.codenation.CentralDeErros.security;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("client")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token", "authorization_code")
                .scopes("read", "write")
                .resourceIds("oauth2-resource")
                .secret("secret");

    }
}
