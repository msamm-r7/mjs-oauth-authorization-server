package com.rapid7.poc.oauth.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.rapid7.poc.oauth.service.CustomUserDetailsService;


public class OAuth2AuthServerConfiguration {

    private static final String AUTH_SERVER_RESOURCE_ID     = "mjs-poc-auth-server";
    private static final String RESOURCE_SERVER_RESOURCE_ID = "mjs-poc-resource-server";

    
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private TokenStore tokenStore = new InMemoryTokenStore();

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private CustomUserDetailsService userDetailsService;
        
        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            // so Resource server can use the access token checking ...
            security.checkTokenAccess("permitAll()");
            security.allowFormAuthenticationForClients();
        };

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
            // @formatter:off
            endpoints
                .tokenStore(this.tokenStore)
                .authenticationManager(this.authenticationManager)
                .userDetailsService(userDetailsService);
            // @formatter:on
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // @formatter:off
            clients
                .inMemory()
                    .withClient("clientapp")
                        .authorizedGrantTypes("client_credentials", "password", "refresh_token", "check_token")
                        //.authorizedGrantTypes("client_credentials", "refresh_token", "check_token")
                        .authorities("USER")
                        .scopes("read", "write")
                        .resourceIds(AUTH_SERVER_RESOURCE_ID, RESOURCE_SERVER_RESOURCE_ID)
                        .secret("123456");
            // @formatter:on
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore);
            return tokenServices;
        }

    }
    
}
