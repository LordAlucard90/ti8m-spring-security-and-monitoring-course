package ch.ti8m.academy.oauth2;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This Resource Server is a backend service that hosts and protects access to resources (APIs, data, etc.) that clients want to access.
 * The Resource Server is responsible for validating the access token provided by the client and granting access to the requested resources only if the token is valid and has the necessary permissions (scopes).
 */
@SpringBootApplication
@Log4j2
public class ResourceServerApp {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApp.class, args);
        log.info("Startup sequence completed.");
    }
}