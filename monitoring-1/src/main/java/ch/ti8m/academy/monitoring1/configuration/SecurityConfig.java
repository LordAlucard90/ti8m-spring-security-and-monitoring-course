package ch.ti8m.academy.monitoring1.configuration;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AdminServerProperties adminServer;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // just for this example, do not use it on production
        final var adminContextPath = adminServer.getContextPath();
        final var openEndpoints = new String[]{
                "/messages/greet",
                "/actuator/health",
                adminContextPath + "/assets/**",
                adminContextPath + "/login"
        };

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(openEndpoints).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage(adminContextPath + "/login")
                        .successHandler(generateSuccessHandler())
                )
                .logout(logout -> logout
                        .logoutUrl(adminContextPath + "/logout")
                )
                .rememberMe(rememberMe -> rememberMe
                        .key(UUID.randomUUID().toString())
                        .tokenValiditySeconds(1209600)
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        return http.build();
    }

    private SavedRequestAwareAuthenticationSuccessHandler generateSuccessHandler() {
        var successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminServer.getContextPath() + "/");
        return successHandler;
    }
}