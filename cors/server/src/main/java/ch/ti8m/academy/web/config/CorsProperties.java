package ch.ti8m.academy.web.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.cors")
public record CorsProperties(
    String pathMappings,
    List<String> allowedOrigins,
    List<String> allowedMethods,
    List<String> allowedHeaders
) {

}
