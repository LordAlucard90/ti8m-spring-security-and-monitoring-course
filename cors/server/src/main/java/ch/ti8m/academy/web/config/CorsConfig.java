package ch.ti8m.academy.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * CORS configuration: CORS must be processed before Spring Security because the pre-flight request will not contain any cookies.
 * Therefore, the request would determine the user is not authenticated and reject it.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(CorsProperties corsProperties) {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsProperties.allowedOrigins());
        configuration.setAllowedMethods(corsProperties.allowedMethods());
        configuration.setAllowedHeaders(corsProperties.allowedHeaders());

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProperties.pathMappings(), configuration);
        return source;
    }
}
