package ch.ti8m.academy.security.apikey.solution.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("messages")
public class MessageController {
    @GetMapping("default/open-who-am-i")
    public WhoAmIDto openWhoAmI() {
        return WhoAmIDto.fromSecurityContext();
    }

    @GetMapping("default/who-am-i")
    public WhoAmIDto whoAmI() {
        return WhoAmIDto.fromSecurityContext();
    }
}
