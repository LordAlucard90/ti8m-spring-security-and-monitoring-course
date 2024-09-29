package ch.ti8m.academy.web.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * CORS with Spring Security
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            // CORS: by default Spring uses a bean with the name of corsConfigurationSource
            .cors(withDefaults())
            .httpBasic(withDefaults())

            .authorizeHttpRequests(
                auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}