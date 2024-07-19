package ch.ti8m.academy.security.basic2.solution.message;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageControllerSecurityIT {
    private static final String CSV_HEADER = "username;password";
    private static final String CSV_ALICE = "alice@example.com;password-a";
    private static final String CSV_BOB = "bob@example.com;password-b";
    private static final String CSV_CHARLY = "charly@example.com;password-c";
    private static final String CSV_DANIEL = "daniel@example.com;password-d";

    @Autowired
    private TestRestTemplate restTemplate;


    @Nested
    class OpenEndpointTests {
        private final String basePath = "/messages/default/open";

        @Test
        void givenNoUser_thenIsOk() throws Exception {
            var response = restTemplate
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            var body = response.getBody();
            assertThat(body).isNotNull();
            assertThat(body.getMessage()).isEqualTo("open to everyone");
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_ALICE,
                        CSV_BOB,
                        CSV_CHARLY,
                        CSV_DANIEL,
                }
        )
        void givenAuthorizedUser_thenIsOk(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            var body = response.getBody();
            assertThat(body).isNotNull();
            assertThat(body.getMessage()).isEqualTo("open to everyone");
        }
    }

    @Nested
    class AuthenticatedEndpointTests {
        private final String basePath = "/messages/default/authenticated";

        @Test
        void givenNoUser_thenIsUnauthorized() {
            var response = restTemplate
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }

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
        void givenAuthorizedUser_thenIsOk(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            var body = response.getBody();
            assertThat(body).isNotNull();
            assertThat(body.getMessage()).isEqualTo("available to authenticated");
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_DANIEL,
                }
        )
        void givenNotAuthorizedUser_thenIsUnauthorized(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Nested
    class UserEndpointTests {
        private final String basePath = "/messages/default/user";

        @Test
        void givenNoUser_thenIsUnauthorized() {
            var response = restTemplate
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }

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
        void givenAuthorizedUser_thenIsOk(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            var body = response.getBody();
            assertThat(body).isNotNull();
            assertThat(body.getMessage()).isEqualTo("available to user");
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_DANIEL,
                }
        )
        void givenNotAuthorizedUser_thenIsUnauthorized(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Nested
    class StaffEndpointTests {
        private final String basePath = "/messages/default/staff";

        @Test
        void givenNoUser_thenIsUnauthorized() {
            var response = restTemplate
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_ALICE,
                        CSV_BOB,
                }
        )
        void givenAuthorizedUser_thenIsOk(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            var body = response.getBody();
            assertThat(body).isNotNull();
            assertThat(body.getMessage()).isEqualTo("available to staff");
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_CHARLY,
                }
        )
        void givenUserWithoutRights_thenIsForbidden(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, Object.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_DANIEL,
                }
        )
        void givenNotAuthorizedUser_thenIsUnauthorized(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Nested
    class AdminEndpointTests {
        private final String basePath = "/messages/default/admin";

        @Test
        void givenNoUser_thenIsUnauthorized() {
            var response = restTemplate
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_ALICE,
                }
        )
        void givenAuthorizedUser_thenIsOk(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            var body = response.getBody();
            assertThat(body).isNotNull();
            assertThat(body.getMessage()).isEqualTo("available to admin");
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_BOB,
                        CSV_CHARLY,
                }
        )
        void givenUserWithoutRights_thenIsForbidden(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, Object.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        }

        @ParameterizedTest
        @CsvSource(
                useHeadersInDisplayName = true,
                delimiterString = ";",
                value = {
                        CSV_HEADER,
                        CSV_DANIEL,
                }
        )
        void givenNotAuthorizedUser_thenIsUnauthorized(final String username, final String password) {
            var response = restTemplate
                    .withBasicAuth(username, password)
                    .getForEntity(basePath, MessageDto.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }
}
