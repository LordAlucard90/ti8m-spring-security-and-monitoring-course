package ch.ti8m.academy.security.apikey.solution.message;

import ch.ti8m.academy.security.apikey.solution.configuration.UserRole;
import ch.ti8m.academy.security.apikey.solution.user.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public record WhoAmIDto(
        String user,
        UserRole role
) {
    public static WhoAmIDto fromSecurityContext() {
        // the current security context can be retrieved by the security context holder
        var securityContext = SecurityContextHolder.getContext();
        // the authentication is available from the current security context
        var authentication = securityContext.getAuthentication();
        // the principal is accessible from the Authentication
        if (authentication.getPrincipal() instanceof UserEntity user) {
            return new WhoAmIDto(
                    user.getUsername(),
                    user.getRole()
            );
        }
        return new WhoAmIDto("Anonymous", null);
    }
}
