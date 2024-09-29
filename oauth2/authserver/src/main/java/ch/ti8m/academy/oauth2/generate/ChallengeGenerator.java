package ch.ti8m.academy.oauth2.generate;

import static lombok.AccessLevel.PRIVATE;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * The verifier can be generated with the {@link VerifierGenerator}. But the value can be any string.
 */
@Log4j2
@NoArgsConstructor(access = PRIVATE)
public final class ChallengeGenerator {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String verifier = null; // TODO: generate and add a verifier
        String challenge = generateChallenge(verifier);
        log.info("Challenge: {}", challenge);
    }

    private static String generateChallenge(String verifier) throws NoSuchAlgorithmException {
        var messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] digested = messageDigest.digest(verifier.getBytes());
        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(digested);
    }
}
