package ch.ti8m.academy.oauth2.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(OAuth2AuthenticationToken authentication) {
        log.info("Authentication: " + authentication.getPrincipal());
        log.info("Authorized Client Registration Id: {}", authentication.getAuthorizedClientRegistrationId());
        return "index.html";
    }
}
