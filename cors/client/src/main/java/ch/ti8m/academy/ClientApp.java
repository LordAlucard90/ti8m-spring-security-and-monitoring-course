package ch.ti8m.academy;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * This application presents a default html page located at <a href="http://localhost:9090/index.html">localhost:9090</a> and
 * displays data that is retrieved from <a href="http://localhost:8080/greeting">localhost:8080/greeting</a>
 */
@SpringBootApplication
@Log4j2
@ConfigurationPropertiesScan
public class ClientApp {

    public static void main(String[] args) {
        new SpringApplication(ClientApp.class).run(args);
        log.info("Client startup sequence completed.");
    }
}