package ch.ti8m.academy.security.basic2.message;

import lombok.extern.slf4j.Slf4j;
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
    // TODO: use annotation to (pre-)authorize authenticated users
    public MessageDto authenticated() {
        return new MessageDto("available to authenticated");
    }

    @GetMapping("default/user")
    // TODO: use annotation to authorize all alice, bob and charly
    public MessageDto user() {
        return new MessageDto("available to user");
    }

    @GetMapping("default/staff")
    // TODO: use annotation to authorize all alice and bob
    public MessageDto staff() {
        return new MessageDto("available to staff");
    }

    @GetMapping("default/admin")
    // TODO: use annotation to authorize only alice
    public MessageDto admin() {
        return new MessageDto("available to admin");
    }
}
