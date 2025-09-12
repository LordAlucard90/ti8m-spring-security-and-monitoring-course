package ch.ti8m.academy.security.apikey.configuration;

import ch.ti8m.academy.security.apikey.user.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiKeyAuthentication implements Authentication {
    // the principal in this example is represented by the whole entity for convenience,
    // it should be a dto with the needed information only
    private UserEntity principal;
    private boolean authenticated;

    public static ApiKeyAuthentication from(UserEntity user) {
        return new ApiKeyAuthentication(
                user,
                // with this logic a disable user is still able to access to open endpoints
                // it is possible to do not allow this by throwing an exception in the filter
                !user.isDisabled()
        );
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        // TODO: return the name from the principal (username)
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: return the authorities from the principal
        return List.of();
    }

    @Override
    public Object getCredentials() {
        // TODO: do not return the credentials, return null instead)
        return this.principal.getApiKey();
    }

    @Override
    public Object getDetails() {
        // other optional details as ip, sessionId, etc.
        return null;
    }
}
