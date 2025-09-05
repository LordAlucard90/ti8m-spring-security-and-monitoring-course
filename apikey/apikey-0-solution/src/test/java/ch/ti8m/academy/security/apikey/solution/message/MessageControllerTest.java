package ch.ti8m.academy.security.apikey.solution.message;

import ch.ti8m.academy.security.apikey.solution.configuration.UserRole;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageControllerTest {
    private static final String CSV_HEADER = "apiKey;username;role";
    private static final String CSV_ANONYMOUS = ";Anonymous;";
    private static final String CSV_ALICE = "api-key-a;alice@example.com;ADMIN";
    private static final String CSV_BOB = "api-key-b;bob@example.com;STAFF";
    private static final String CSV_CHARLY = "api-key-c;charly@example.com;USER";
    private static final String CSV_DANIEL = "api-key-d;daniel@example.com;USER";

    @Autowired
    private TestRestTemplate restTemplate;


    @Nested
    class OpenEndpointTests {
        private final String basePath = "/messages/default/open-who-am-i";

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_ANONYMOUS,
                        CSV_ALICE,
                        CSV_BOB,
                        CSV_CHARLY,
                        CSV_DANIEL,
                }
        )
        void givenAuthorizedUser_thenIsOk(final String apiKey,
                                          final String username,
                                          final UserRole role) {
            var headers = new HttpHeaders();
            headers.add("X-API-KEY", apiKey);
            var response = restTemplate
                    .exchange(basePath, HttpMethod.GET, new HttpEntity<>(headers), WhoAmIDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            var body = response.getBody();
            assertThat(body).isNotNull();
            assertThat(body.user()).isEqualTo(username);
            assertThat(body.role()).isEqualTo(role);
        }
    }

    @Nested
    class AuthenticatedEndpointTests {
        private final String basePath = "/messages/default/who-am-i";

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_ALICE,
                        CSV_BOB,
                        CSV_CHARLY,
                }
        )
        void givenAuthorizedUser_thenIsOk(final String apiKey,
                                          final String username,
                                          final UserRole role) {
            var headers = new HttpHeaders();
            headers.add("X-API-KEY", apiKey);
            var response = restTemplate
                    .exchange(basePath, HttpMethod.GET, new HttpEntity<>(headers), WhoAmIDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            var body = response.getBody();
            assertThat(body).isNotNull();
            assertThat(body.user()).isEqualTo(username);
            assertThat(body.role()).isEqualTo(role);
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_ANONYMOUS,
                        CSV_DANIEL,
                }
        )
        void givenNotAuthorizedUser_thenIsUnauthorized(final String apiKey,
                                                       final String username,
                                                       final UserRole role) {
            var headers = new HttpHeaders();
            headers.add("X-API-KEY", apiKey);
            var response = restTemplate
                    .exchange(basePath, HttpMethod.GET, new HttpEntity<>(headers), WhoAmIDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        }
    }
}