package ch.ti8m.academy.oauth2;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Authorization Server is a service that implements the OAuth 2.0 protocol, handling tasks such as issuing access tokens, managing user authentication,
 * and controlling the permissions (or scopes) granted to clients.
 * The Authorization Server is responsible for securing APIs and providing secure access to resources by issuing tokens to clients, which can be used to access protected resources.
 */
@SpringBootApplication
@Log4j2
public class AuthServerApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApp.class, args);
        log.info("Startup sequence completed.");
    }
}