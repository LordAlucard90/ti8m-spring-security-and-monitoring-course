package ch.ti8m.academy.security.basic1.solution.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("messages")
public class MessageController {
    @GetMapping("default/open")
    public MessageDto open() {
        return new MessageDto("open to everyone");
    }

    @GetMapping("default/authenticated")
    public MessageDto authenticated() {
        return new MessageDto("available to authenticated");
    }

    @GetMapping("default/staff")
    public MessageDto staff() {
        return new MessageDto("available to staff");
    }

    @GetMapping("default/admin")
    public MessageDto admin() {
        return new MessageDto("available to admin");
    }
}
