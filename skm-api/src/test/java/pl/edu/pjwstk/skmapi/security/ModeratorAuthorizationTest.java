package pl.edu.pjwstk.skmapi.security;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashMap;
import java.util.Set;

@SpringBootTest
@WithMockUser(authorities = {"ROLE_MODERATOR"})
class ModeratorAuthorizationTest extends GenericAuthorizationTest {

    @BeforeAll
    static void setAccessibleURIs() {
        HashMap<String, Set<String>> uris = new HashMap<>();
        uris.put("/login", Set.of("POST"));
        uris.put("/trains", Set.of("GET", "POST", "PUT"));
        uris.put("/trains/", Set.of("GET", "DELETE"));
        uris.put("/compartments", Set.of("GET", "POST", "PUT"));
        uris.put("/compartments/", Set.of("GET", "DELETE"));
        uris.put("/move", Set.of("POST"));
        accessibleURIs = uris;
    }
}