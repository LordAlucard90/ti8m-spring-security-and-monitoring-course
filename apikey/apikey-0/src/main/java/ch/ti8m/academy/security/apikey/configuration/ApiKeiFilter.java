package ch.ti8m.academy.security.apikey.configuration;

import ch.ti8m.academy.security.apikey.user.UserEntity;
import ch.ti8m.academy.security.apikey.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiKeiFilter extends OncePerRequestFilter {
    private static final String API_KEY = "X-API-KEY";
    // TODO: retrieve the SecurityContextHolderStrategy (tip: use the SecurityContextHolder)
    private final SecurityContextHolderStrategy securityContextHolderStrategy = null;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get api key from header
        // TODO: retrieve the api key from the request header
        var apiKey = (String) null;
        log.debug("ApiKey: {}", apiKey);

        // TODO: retrieve the user (tip: use the repository)
        var optionalUser = (Optional<UserEntity>) null;
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            log.debug("User: {}", user.getUsername());

            // TODO: create a new ApiKeyAuthentication (tip: use the factory method)
            var apiKeyAuth = (ApiKeyAuthentication) null;
            // TODO: create an new SecurityContext (tip: use SecurityContextHolderStrategy)
            var securityContext = (SecurityContext) null;
            // TODO: set the ApiKeyAuthentication in the SecurityContext
            // TODO: set the SecurityContext in the SecurityContextHolder (tip: use SecurityContextHolderStrategy)
        }

        // forward request with to the next filter.
        // In case the authorization is required
        // it is also possible to throw and AccessDeniedException
        // instead of forwarding the request to the next chain element
        filterChain.doFilter(request, response);
    }
}