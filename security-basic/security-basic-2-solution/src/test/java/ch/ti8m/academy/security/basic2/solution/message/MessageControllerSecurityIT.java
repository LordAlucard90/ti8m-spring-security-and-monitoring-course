package ch.ti8m.academy.security.basic2.solution.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureRestTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageControllerSecurityIT {
    private static final String CSV_HEADER = "username;password";
    private static final String CSV_ALICE = "alice@example.com;password-a";
    private static final String CSV_BOB = "bob@example.com;password-b";
    private static final String CSV_CHARLY = "charly@example.com;password-c";
    private static final String CSV_DANIEL = "daniel@example.com;password-d";

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private RestTestClient testClient;

    @BeforeEach
    void setUp() {
        testClient = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:%d/%s".formatted(port, contextPath))
                .configureMessageConverters(HttpMessageConverters.Builder::registerDefaults)
                .build();
    }

    @Nested
    class OpenEndpointTests {
        private final String basePath = "/messages/default/open";

        @Test
        void givenNoUser_thenIsOk() throws Exception {
            var body = testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(MessageDto.class).getResponseBody();

            assertThat(body).isNotNull();
            assertThat(body.message()).isEqualTo("open to everyone");
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
            var body = testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(MessageDto.class).getResponseBody();

            assertThat(body).isNotNull();
            assertThat(body.message()).isEqualTo("open to everyone");
        }
    }

    @Nested
    class AuthenticatedEndpointTests {
        private final String basePath = "/messages/default/authenticated";

        @Test
        void givenNoUser_thenIsUnauthorized() {
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isUnauthorized();
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
            var body = testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(MessageDto.class).getResponseBody();

            assertThat(body).isNotNull();
            assertThat(body.message()).isEqualTo("available to authenticated");
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
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isUnauthorized();
        }
    }

    @Nested
    class UserEndpointTests {
        private final String basePath = "/messages/default/user";

        @Test
        void givenNoUser_thenIsUnauthorized() {
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isUnauthorized();
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
            var body = testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(MessageDto.class).getResponseBody();

            assertThat(body).isNotNull();
            assertThat(body.message()).isEqualTo("available to user");
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
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isUnauthorized();
        }
    }

    @Nested
    class StaffEndpointTests {
        private final String basePath = "/messages/default/staff";

        @Test
        void givenNoUser_thenIsUnauthorized() {
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isUnauthorized();
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
            var body = testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(MessageDto.class).getResponseBody();

            assertThat(body).isNotNull();
            assertThat(body.message()).isEqualTo("available to staff");
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
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isForbidden();
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
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .returnResult(MessageDto.class).getResponseBody();
        }
    }

    @Nested
    class AdminEndpointTests {
        private final String basePath = "/messages/default/admin";

        @Test
        void givenNoUser_thenIsUnauthorized() {
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isUnauthorized();
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
            var body = testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(MessageDto.class).getResponseBody();

            assertThat(body).isNotNull();
            assertThat(body.message()).isEqualTo("available to admin");
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
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isForbidden();
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
            testClient
                    .get()
                    .uri(basePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .returnResult(MessageDto.class).getResponseBody();
        }
    }
}
