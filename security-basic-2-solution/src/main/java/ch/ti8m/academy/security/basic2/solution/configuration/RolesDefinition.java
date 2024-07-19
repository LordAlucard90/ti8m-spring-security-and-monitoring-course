package ch.ti8m.academy.security.basic2.solution.configuration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RolesDefinition {
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String ADMIN_AUTHORITY = "ROLE_ADMIN";
    public static final String STAFF_ROLE = "STAFF";
    public static final String STAFF_AUTHORITY = "ROLE_STAFF";
    public static final String USER_ROLE = "USER";
    public static final String USER_AUTHORITY = "ROLE_USER";
}
