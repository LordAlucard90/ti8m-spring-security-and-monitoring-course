package ch.ti8m.academy.security.apikey.solution.configuration;

import ch.ti8m.academy.security.apikey.solution.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiKeiFilter extends OncePerRequestFilter {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get api key from header
        var apiKey = request.getHeader("X-API-KEY");
        log.debug("ApiKey: {}", apiKey);

        var optionalUser = userRepository.findByApiKey(apiKey);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            log.debug("User: {}", user.getUsername());

            // configure authentication context
            var apiKeyAuth = ApiKeyAuthentication.from(user);
            var securityContext = securityContextHolderStrategy.createEmptyContext();
            securityContext.setAuthentication(apiKeyAuth);
            securityContextHolderStrategy.setContext(securityContext);
        }

        // forward request with to the next filter.
        // In case the authorization is required
        // it is also possible to throw and AccessDeniedException
        // instead of forwarding the request to the next chain element
        filterChain.doFilter(request, response);
    }
}