package ch.ti8m.academy.monitoring.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class MonitoringAdminBoard {
    static void main(String[] args) {
        SpringApplication.run(MonitoringAdminBoard.class, args);
    }
}