package ch.ti8m.academy.security.apikey.user;

import ch.ti8m.academy.security.apikey.configuration.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity {
    @Id
    private String username;

    @ToString.Exclude
    private String apiKey;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean disabled;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return Optional.ofNullable(role)
                .map(UserRole::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .map(List::of)
                .orElseGet(List::of);
    }
}
