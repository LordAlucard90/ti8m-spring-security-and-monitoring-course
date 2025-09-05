package ch.ti8m.academy.security.apikey.solution.message;

import ch.ti8m.academy.security.apikey.solution.configuration.UserRole;
import ch.ti8m.academy.security.apikey.solution.user.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public record WhoAmIDto(
        String user,
        UserRole role
) {
    public static WhoAmIDto fromSecurityContext() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity user) {
            return new WhoAmIDto(
                    user.getUsername(),
                    user.getRole()
            );
        }
        return new WhoAmIDto("Anonymous", null);
    }
}
