package ch.ti8m.academy.security.basic.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // TODO: define the string roles:
    //  - STAFF
    //  - ADMIN
    private static final String[] WHITE_LIST = new String[]{
            "/messages/default/open",
    };
    // TODO: define the endpoints that belong to each role

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // open white list endpoints
                        .requestMatchers(WHITE_LIST).permitAll()
                        // TODO: configure the security o require the specified role(s)
                        // require all others authenticated
                        .anyRequest().authenticated()
                );

        // configure authentication type
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // TODO: generate the password encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // ...
//    }

    // TODO: create alice and bob and use them to populate the UserDerailsManager
    // HINT: the password encoder is needed to encrypt the password
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUsers(PasswordEncoder encoder) {
//        var alice = User.withUsername("...")
//                // ...
//                .build();
//        var bob = // ...
//        return new InMemoryUserDetailsManager(/*,,,*/);
//    }
}
