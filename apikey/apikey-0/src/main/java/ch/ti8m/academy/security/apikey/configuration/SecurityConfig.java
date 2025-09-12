package ch.ti8m.academy.security.apikey.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // define endpoints categories
    private static final String[] WHITE_LIST = new String[]{
            "/h2",
            "/h2/**",
            // this is needed due to .authenticated() global requirement
            "/messages/default/open-who-am-i",
    };

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http, ApiKeiFilter apiKeiFilter) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // open white list endpoints
                        .requestMatchers(WHITE_LIST).permitAll()
                        // require all others authenticated
                        .anyRequest().authenticated()
                );
        // configure open endpoints
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        final var h2PathMatcher = PathPatternRequestMatcher.withDefaults().matcher("/h2/**");
        http.csrf(csrf -> csrf.ignoringRequestMatchers(h2PathMatcher));
        // TODO: add aki key authorization filter before the AnonymousAuthenticationFilter
        return http.build();
    }

    @Bean
    // Informs spring that ApiKeiFilter is only registered by the HttpSecurity
    public FilterRegistrationBean<ApiKeiFilter> apiKeyFilterRegistration(ApiKeiFilter filter) {
        var registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}
