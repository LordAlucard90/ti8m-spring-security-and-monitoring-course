package ch.ti8m.academy.security.basic2.user;

import ch.ti8m.academy.security.basic2.configuration.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "users")
public class UserEntity {
    @Id
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean disabled;

    @Transient
    public String describe() {
        return String.format(
                "User(username=%s, role=%s, isDisable=%s)",
                username,
                role,
                disabled
        );
    }
}
