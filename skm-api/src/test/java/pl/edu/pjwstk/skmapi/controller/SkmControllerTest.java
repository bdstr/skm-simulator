package pl.edu.pjwstk.skmapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.pjwstk.skmapi.service.SkmService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SkmController.class)
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
class SkmControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SkmService skmService;

    @Test
    void moveForwardCallsMethodInSkmService() throws Exception {
        String uri = "/move";

        mockMvc.perform(post(uri))
                .andExpect(status().isOk());

        verify(skmService).moveSimulationStepForward();
    }
}