package ch.ti8m.academy.security.basic1.message;

import ch.ti8m.academy.security.basic1.configuration.SecurityConfig;
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

    private static final RequestPostProcessor ALICE = httpBasic("alice", "password-a");
    private static final RequestPostProcessor BOB = httpBasic("bob", "password-b");
    private static final RequestPostProcessor CHARLY = httpBasic("CHARLY", "password-c");

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
        void givenUser_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/open")
                                    .with(BOB)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("open to everyone"));
        }

        @Test
        void givenStaff_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/open")
                                    .with(BOB)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("open to everyone"));
        }

        @Test
        void givenAdmin_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/open")
                                    .with(ALICE)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("open to everyone"));
        }
    }

    @Nested
    class AuthenticatedEndpointTests {
        @Test
        void givenNoUser_thenIsUnauthorized() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/authenticated")
                    )
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void givenUser_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/authenticated")
                                    .with(CHARLY)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("available to authenticated"));
        }

        @Test
        void givenStaff_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/authenticated")
                                    .with(BOB)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("available to authenticated"));
        }

        @Test
        void givenAdmin_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/authenticated")
                                    .with(ALICE)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("available to authenticated"));
        }
    }

    @Nested
    class StaffEndpointTests {
        @Test
        void givenNoUser_thenIsUnauthorized() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/staff")
                    )
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void givenUser_thenIsForbidden() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/staff")
                                    .with(CHARLY)

                    )
                    .andExpect(status().isForbidden());
        }

        @Test
        void givenStaff_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/staff")
                                    .with(BOB)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("available to staff"));
        }

        @Test
        void givenAdmin_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/staff")
                                    .with(ALICE)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("available to staff"));
        }
    }

    @Nested
    class AdminEndpointTests {
        @Test
        void givenNoUser_thenIsUnauthorized() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/admin")
                    )
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void givenUser_thenIsForbidden() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/admin")
                                    .with(CHARLY)

                    )
                    .andExpect(status().isForbidden());
        }

        @Test
        void givenStaff_thenIsForbidden() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/admin")
                                    .with(BOB)

                    )
                    .andExpect(status().isForbidden());
        }

        @Test
        void givenAdmin_thenIsOk() throws Exception {
            mockMvc
                    .perform(
                            get("/messages/default/admin")
                                    .with(ALICE)

                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("available to admin"));
        }
    }
}
