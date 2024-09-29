package ch.ti8m.academy.oauth2.generate;

import static lombok.AccessLevel.PRIVATE;

import java.security.SecureRandom;
import java.util.Base64;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = PRIVATE)
public final class VerifierGenerator {

    public static void main(String[] args) {
        var verifier = generateVerifier();
        log.info("Verifier: {}", verifier);
    }

    private static String generateVerifier() {
        var secureRandom = new SecureRandom();
        byte[] code = new byte[32];
        secureRandom.nextBytes(code);
        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(code);
    }
}
