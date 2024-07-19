package ch.ti8m.academy.security.basic2.solution.message;

import ch.ti8m.academy.security.basic2.solution.configuration.RolesDefinition;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("messages")
public class MessageController {
    @GetMapping("default/open")
    // this will not work due to .authenticated() global requirement
    // @PreAuthorize("permitAll()")
    public MessageDto open() {
        return new MessageDto("open to everyone");
    }

    @GetMapping("default/authenticated")
    @PreAuthorize("isAuthenticated()")
    public MessageDto authenticated() {
        return new MessageDto("available to authenticated");
    }

    @GetMapping("default/user")
    // requires securedEnabled = true and a list of authorities
    @Secured({
            RolesDefinition.USER_AUTHORITY,
            RolesDefinition.STAFF_AUTHORITY,
            RolesDefinition.ADMIN_AUTHORITY
    })
    public MessageDto user() {
        return new MessageDto("available to user");
    }

    @GetMapping("default/staff")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public MessageDto staff() {
        return new MessageDto("available to staff");
    }

    @GetMapping("default/admin")
    // requires jsr250Enabled = true and a list of roles
    @RolesAllowed({
            RolesDefinition.ADMIN_ROLE
    })
    public MessageDto admin() {
        return new MessageDto("available to admin");
    }
}
