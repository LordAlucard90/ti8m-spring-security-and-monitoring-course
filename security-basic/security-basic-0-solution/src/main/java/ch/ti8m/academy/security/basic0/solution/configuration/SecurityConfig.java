package ch.ti8m.academy.security.basic0.solution.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] whiteList = new String[]{
            "/messages/default/open",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // open white list endpoints
                        .requestMatchers(whiteList).permitAll()
                        // require all others authenticated
                        .anyRequest().authenticated()
                );

        // configure authentication type
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
