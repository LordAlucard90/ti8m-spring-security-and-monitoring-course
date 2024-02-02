package ch.ti8m.academy.security.basic.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // TODO: open /default/open endpoint to anyone using a security configuration
//    @Bean
//    public SecurityFilterChain securityOverall(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        // ...
//                );
//
//        return http.build();
//    }
}
