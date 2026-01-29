package ch.ti8m.academy.security.apikey.message;

import ch.ti8m.academy.security.apikey.configuration.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
    private static final String CSV_HEADER = "apiKey;username;role";
    private static final String CSV_ANONYMOUS = ";Anonymous;";
    private static final String CSV_ALICE = "api-key-a;alice@example.com;ADMIN";
    private static final String CSV_BOB = "api-key-b;bob@example.com;STAFF";
    private static final String CSV_CHARLY = "api-key-c;charly@example.com;USER";
    private static final String CSV_DANIEL = "api-key-d;daniel@example.com;USER";

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
            var body = testClient
                    .get()
                    .uri(basePath)
                    .header("X-API-KEY", apiKey)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(WhoAmIDto.class).getResponseBody();

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
            var body = testClient
                    .get()
                    .uri(basePath)
                    .header("X-API-KEY", apiKey)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(WhoAmIDto.class).getResponseBody();

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
                                                       final String _username,
                                                       final UserRole _role) {
            testClient
                    .get()
                    .uri(basePath)
                    .header("X-API-KEY", apiKey)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isForbidden();
        }
    }
}
