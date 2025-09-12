package ch.ti8m.academy.monitoring2.solution.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // just for this example, do not use it on production
        final var openEndpoints = new String[]{
                "/messages/greet",
                "/actuator/health",
        };

        http
                .authorizeHttpRequests(authorize -> authorize
                        // open the health and the exercise endpoint
                        .requestMatchers(openEndpoints).permitAll()
                        // protect the other actuator endpoints
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}