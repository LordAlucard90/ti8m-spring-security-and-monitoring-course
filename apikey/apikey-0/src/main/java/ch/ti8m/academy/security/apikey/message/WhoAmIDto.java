package ch.ti8m.academy.security.apikey.message;

import ch.ti8m.academy.security.apikey.configuration.UserRole;
import ch.ti8m.academy.security.apikey.user.UserEntity;

public record WhoAmIDto(
        String user,
        UserRole role
) {
    public static WhoAmIDto fromSecurityContext() {
        // TODO: retrieve the principal information (tip: use the SecurityContextHolder)
        var principal = (Object) null;
        if (principal instanceof UserEntity user) {
            return new WhoAmIDto(
                    user.getUsername(),
                    user.getRole()
            );
        }
        return new WhoAmIDto("Anonymous", null);
    }
}
