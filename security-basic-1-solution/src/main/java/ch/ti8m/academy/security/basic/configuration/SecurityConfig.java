package ch.ti8m.academy.security.basic.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String STAFF_ROLE = "STAFF";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String[] WHITE_LIST = new String[]{
            "/messages/default/open",
    };
    private static final String[] STAFF_ENDPOINTS = new String[]{
            "/messages/default/staff",
    };
    private static final String[] ADMIN_ENDPOINTS = new String[]{
            "/messages/default/admin",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // open white list endpoints
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(STAFF_ENDPOINTS).hasRole(STAFF_ROLE)
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole(ADMIN_ROLE)
                        // require all others authenticated
                        .anyRequest().authenticated()
                );

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUsers(PasswordEncoder encoder) {
        var alice = User.withUsername("alice")
                .password(encoder.encode("password-a"))
                .roles(ADMIN_ROLE, STAFF_ROLE)
                .build();
        var bob = User.withUsername("bob")
                .password(encoder.encode("password-b"))
                .roles(STAFF_ROLE)
                .build();
        var users = List.of(alice, bob);
        return new InMemoryUserDetailsManager(users);
    }
}
