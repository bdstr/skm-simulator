package pl.edu.pjwstk.skmapi.security;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
abstract class GenericAuthorizationTest {

    static HashMap<String, Set<String>> accessibleURIs;

    @Autowired
    MockMvc mockMvc;

    @Test
    void roleHaveAccessToPostLogin() throws Exception {
        String uri = "/login";
        String verb = "POST";
        String content = """
                {
                    "username": "user",
                    "password": "user"
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    void roleHaveAccessToPostMove() throws Exception {
        String uri = "/move";
        String verb = "POST";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(post(uri))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(post(uri))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(1)
    void roleHaveAccessToGetTrainsList() throws Exception {
        String uri = "/trains";
        String verb = "GET";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(get(uri))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get(uri))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(2)
    void roleHaveAccessToAddTrain() throws Exception {
        String uri = "/trains";
        String verb = "POST";
        String content = """
                {
                    "direction": 1,
                    "currentStation": "GDANSK_OLIWA",
                    "waitedTimeOnLastStation": 0
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isAccepted());
        } else {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(3)
    void roleHaveAccessToUpdateTrain() throws Exception {
        String uri = "/trains";
        String verb = "PUT";
        String content = """
                {
                    "id": 1,
                    "direction": 1,
                    "currentStation": "GDANSK_OLIWA",
                    "waitedTimeOnLastStation": 0
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isAccepted());
        } else {
            mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(4)
    void roleHaveAccessToGetTrainInfo() throws Exception {
        String uri = "/trains/";
        String verb = "GET";
        String id = "1";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(get(uri + id))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get(uri + id))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(5)
    void roleHaveAccessToDeleteTrain() throws Exception {
        String uri = "/trains/";
        String verb = "DELETE";
        String id = "2";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(delete(uri + id))
                    .andExpect(status().isAccepted());
        } else {
            mockMvc.perform(delete(uri + id))
                    .andExpect(status().isForbidden());
        }
    }


    @Test
    @Order(1)
    void roleHaveAccessToGetCompartmentsList() throws Exception {
        String uri = "/compartments";
        String verb = "GET";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(get(uri))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get(uri))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(2)
    void roleHaveAccessToAddCompartment() throws Exception {
        String uri = "/compartments";
        String verb = "POST";
        String content = """
                {
                    "capacity": 20,
                    "train": {
                        "id": 1
                    }
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isAccepted());
        } else {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(3)
    void roleHaveAccessToUpdateCompartment() throws Exception {
        String uri = "/compartments";
        String verb = "PUT";
        String content = """
                {
                    "id": 1,
                    "capacity": 20,
                    "train": {
                        "id": 1
                    }
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isAccepted());
        } else {
            mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(4)
    void roleHaveAccessToGetCompartmentInfo() throws Exception {
        String uri = "/compartments/";
        String verb = "GET";
        String id = "1";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(get(uri + id))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get(uri + id))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(5)
    void roleHaveAccessToDeleteCompartment() throws Exception {
        String uri = "/compartments/";
        String verb = "DELETE";
        String id = "2";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(delete(uri + id))
                    .andExpect(status().isAccepted());
        } else {
            mockMvc.perform(delete(uri + id))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(1)
    void roleHaveAccessToGetUsersList() throws Exception {
        String uri = "/users";
        String verb = "GET";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(get(uri))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get(uri))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(2)
    void roleHaveAccessToAddUser() throws Exception {
        String uri = "/users";
        String verb = "POST";
        String content = """
                {
                    "username": "new_user",
                    "password": "password",
                    "authorities": "ROLE_USER"
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isAccepted());
        } else {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(3)
    void roleHaveAccessToUpdateUser() throws Exception {
        String uri = "/users";
        String verb = "PUT";
        String content = """
                {
                    "id": 1,
                    "username": "user",
                    "password": "password",
                    "authorities": "ROLE_USER"
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isAccepted());
        } else {
            mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(4)
    void roleHaveAccessToGetUserInfo() throws Exception {
        String uri = "/users/";
        String verb = "GET";
        String id = "1";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(get(uri + id))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get(uri + id))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @Order(5)
    void roleHaveAccessToDeleteUser() throws Exception {
        String uri = "/users/";
        String verb = "DELETE";
        String id = "2";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(delete(uri + id))
                    .andExpect(status().isAccepted());
        } else {
            mockMvc.perform(delete(uri + id))
                    .andExpect(status().isForbidden());
        }
    }
}