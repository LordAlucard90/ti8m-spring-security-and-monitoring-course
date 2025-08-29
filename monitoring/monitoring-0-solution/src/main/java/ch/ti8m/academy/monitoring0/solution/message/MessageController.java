package ch.ti8m.academy.monitoring0.solution.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("messages")
public class MessageController {
    @GetMapping("greet")
    public MessageDto open(@RequestParam(defaultValue = "nobody") String name) {
        var message = "Welcome %s!".formatted(name);
        return new MessageDto(message);
    }
}
