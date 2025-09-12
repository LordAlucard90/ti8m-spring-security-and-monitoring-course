package ch.ti8m.academy.monitoring.admin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
public class CorsConfig {
    private static final String[] ALLOWED_ORIGINS = new String[]{
            "http://localhost:8302",
            "http://localhost:8303",
    };
    private static final String[] ALLOWED_METHODS = new String[]{
            "POST"
    };
    private static final String[] ALLOWED_HEADERS = new String[]{
            "*"
    };
    private static final String PATH_MAPPING = "/**";

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(ALLOWED_ORIGINS));
        configuration.setAllowedMethods(List.of(ALLOWED_METHODS));
        configuration.setAllowedHeaders(List.of(ALLOWED_HEADERS));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(PATH_MAPPING, configuration);
        return source;
    }
}
