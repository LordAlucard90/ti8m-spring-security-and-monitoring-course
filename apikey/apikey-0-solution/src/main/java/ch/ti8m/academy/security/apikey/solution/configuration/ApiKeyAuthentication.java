package ch.ti8m.academy.security.apikey.solution.configuration;

import ch.ti8m.academy.security.apikey.solution.user.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiKeyAuthentication implements Authentication {
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
        return this.principal.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.principal.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }
}
