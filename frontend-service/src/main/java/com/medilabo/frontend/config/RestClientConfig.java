package com.medilabo.frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(
            @Value("${gateway.base-url}") String gatewayBaseUrl,
            @Value("${gateway.security.username}") String username,
            @Value("${gateway.security.password}") String password) {

        return RestClient.builder()
                .baseUrl(gatewayBaseUrl)
                .defaultHeaders(headers -> headers.setBasicAuth(username, password))
                .build();
    }
}
