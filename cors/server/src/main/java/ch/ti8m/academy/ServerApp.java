package ch.ti8m.academy;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * This application exposes an endpoint at <a href="http://localhost:8080/greeting">localhost:8080/greeting</a> that returns json data with an id and some content.
 */
@SpringBootApplication
@Log4j2
@ConfigurationPropertiesScan
public class ServerApp {

    public static void main(String[] args) {
        new SpringApplication(ServerApp.class).run(args);
        log.info("Server startup sequence completed.");
    }
}