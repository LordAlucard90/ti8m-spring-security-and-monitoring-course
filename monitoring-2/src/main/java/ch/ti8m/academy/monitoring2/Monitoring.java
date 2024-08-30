package ch.ti8m.academy.monitoring2;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class Monitoring {
    public static void main(String[] args) {
        SpringApplication.run(Monitoring.class, args);
    }
}