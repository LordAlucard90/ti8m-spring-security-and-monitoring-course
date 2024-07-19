package ch.ti8m.academy.security.basic2.solution.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {
    // define endpoints categories
    private static final String[] WHITE_LIST = new String[]{
            "/h2",
            "/h2/**",
            // this is needed due to .authenticated() global requirement
            "/messages/default/open",
    };

    @Bean
    // higher priority filter
    @Order(0)
    public SecurityFilterChain whiteListFilterChain(HttpSecurity http) throws Exception {
        http
                // restrict filter to open endpoints
                .securityMatchers(customizer -> customizer
                        .requestMatchers(WHITE_LIST)
                )
                .authorizeHttpRequests(authorize -> authorize
                        // open white list endpoints
                        .anyRequest().permitAll()
                );

        // configure authentication type
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2/**")));

        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // require all others authenticated
                        .anyRequest().authenticated()
                );
        // configure authentication type
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // use default password encoder configuration
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
