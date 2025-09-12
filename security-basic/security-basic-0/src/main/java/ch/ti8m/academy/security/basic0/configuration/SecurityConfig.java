package ch.ti8m.academy.security.basic0.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // TODO:
    //  - open /message/default/open endpoint to anyone
    //  - restrict /message/default/authentivated endpoint to authentivated users
//    @Bean
//    public SecurityFilterChain securityOverall(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        // ...
//                );
//
//          HINT: basic auth can be configured using: Customizer.withDefaults()
//
//        return http.build();
//    }
}
