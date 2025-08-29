package ch.ti8m.academy.monitoring2.message;

import ch.ti8m.academy.monitoring2.metric.MetricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("messages")
public class MessageController {
    private final MetricService metricService;

    @GetMapping("greet")
    public MessageDto open(@RequestParam(defaultValue = "nobody") String name) {
        // TODO: uncomment and use when ready
        // metricService.addRandomGreetDuration();
        // metricService.increaseGreetsCountFor(name);
        // metricService.notifyGreetFor(name);

        var message = "Welcome %s!".formatted(name);
        return new MessageDto(message);
    }
}
