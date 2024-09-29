package ch.ti8m.academy.oauth2.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationConverter converter;

    @Value("${keySetURI}")
    private String keySetUri;

    @Bean
    public SecurityFilterChain asFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
            customizer -> customizer.jwt(
                jwtCustomizer -> jwtCustomizer
                    .jwkSetUri(keySetUri)
                    .jwtAuthenticationConverter(converter)
            )
        );

        http.authorizeHttpRequests(
            c -> c.anyRequest().authenticated()
        );

        return http.build();
    }
}