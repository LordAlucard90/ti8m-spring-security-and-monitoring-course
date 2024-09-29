package ch.ti8m.academy.oauth2.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo(CustomAuthenticationToken authentication) {
        log.info("Claims: ");
        authentication.getToken().getClaims().forEach((k, v) -> log.info("{}: {}", k, v));
        return "Authenticated!";
    }
}
