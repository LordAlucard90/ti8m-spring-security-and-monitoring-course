package ch.ti8m.academy.security.basic.message;

import ch.ti8m.academy.security.basic.configuration.SecurityConfig;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(MessageController.class)
@Import({
        SecurityConfig.class
})
class MessageControllerSecurityIT {
    @Autowired
    private MockMvc mockMvc;

    private static final String USERNAME = "student";
    private static final String PASSWORD = "secret";
    private static final RequestPostProcessor AUTHORIZED_USER = httpBasic(USERNAME, PASSWORD);

    @Nested
    class OpenEndpointTests {
        @Test
        void givenNoUser_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/open")
                    )
                    .andExpect(status().isOk());
        }

        @Test
        void givenAdmin_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/open")
                                    .with(AUTHORIZED_USER)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("open to everyone"));
        }

    }
}