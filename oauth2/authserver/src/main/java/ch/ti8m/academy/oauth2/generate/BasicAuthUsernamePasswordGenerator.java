package ch.ti8m.academy.oauth2.generate;

import static lombok.AccessLevel.PRIVATE;

import java.util.Base64;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ch.ti8m.academy.oauth2.web.SecurityConfig;

@Log4j2
@NoArgsConstructor(access = PRIVATE)
public final class BasicAuthUsernamePasswordGenerator {

    public static void main(String[] args) {
        String username = SecurityConfig.CLIENT_ID;
        String password = SecurityConfig.CLIENT_SECRET;

        String encoded = generateUsernamePassword(username, password);

        log.info("Authorization: Basic {}", encoded);
    }

    /**
     * Base 64 encodes a username and password (format "username:password")
     */
    private static String generateUsernamePassword(String username, String password) {
        var src = "%s:%s".formatted(username, password);
        return Base64.getEncoder().encodeToString(src.getBytes());
    }
}
