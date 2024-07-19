package ch.ti8m.academy.security.basic2.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN(RolesDefinition.ADMIN_ROLE, RolesDefinition.ADMIN_AUTHORITY),
    STAFF(RolesDefinition.STAFF_ROLE, RolesDefinition.STAFF_AUTHORITY),
    USER(RolesDefinition.USER_ROLE, RolesDefinition.USER_AUTHORITY);

    private final String role;
    private final String authority;
}
