package pl.edu.pjwstk.skmapi.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Captor
    ArgumentCaptor<User> argumentCaptor;

    String uri = "/users";

    @Test
    public void getAllShouldReturnListOfUsers() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("password");
        user1.setAuthorities("ROLE_USER");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setPassword("password123");
        user2.setAuthorities("ROLE_MODERAT");

        when(userService.getAll()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(user1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value(user1.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value(user1.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authorities").value(user1.getAuthorities()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(user2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value(user2.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].password").value(user2.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].authorities").value(user2.getAuthorities()));

        verify(userService).getAll();
    }

    @Test
    public void getByIdShouldReturnUserWithGivenId() throws Exception {
        String uri = this.uri + "/1";
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setUsername("user");
        user.setPassword("password");
        user.setAuthorities("ROLE_USER");

        when(userService.getById(id)).thenReturn(user);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(user.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities").value(user.getAuthorities()));

        verify(userService).getById(id);
    }

    @Test
    public void updateShouldCallCreateOrUpdateMethod() throws Exception {
        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "id": 1,
                    "username": "user",
                    "password": "password",
                    "authorities": "ROLE_USER"
                }"""))
                .andExpect(status().isAccepted());

        verify(userService).createOrUpdate(argumentCaptor.capture());

        assertEquals(1L, argumentCaptor.getValue().getId());
        assertEquals("user", argumentCaptor.getValue().getUsername());
        assertEquals("password", argumentCaptor.getValue().getPassword());
        assertEquals("ROLE_USER", argumentCaptor.getValue().getAuthorities());
    }

    @Test
    public void addShouldCallCreateOrUpdateMethod() throws Exception {
        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "username": "user",
                    "password": "password",
                    "authorities": "ROLE_USER"
                }"""))
                .andExpect(status().isAccepted());

        verify(userService).createOrUpdate(argumentCaptor.capture());

        assertEquals("user", argumentCaptor.getValue().getUsername());
        assertEquals("password", argumentCaptor.getValue().getPassword());
        assertEquals("ROLE_USER", argumentCaptor.getValue().getAuthorities());
    }

    @Test
    public void deleteShouldReturnStatusAcceptedWhenUserIsPresentInDatabase() throws Exception {
        String uri = this.uri + "/1";

        mockMvc.perform(delete(uri))
                .andExpect(status().isAccepted());

        verify(userService).delete(1L);
    }

    @Test
    public void deleteShouldReturnStatusNotFoundWhenUserIsNotPresentInDatabase() throws Exception {
        String uri = this.uri + "/1";

        doThrow(EntityNotFoundException.class).when(userService).delete(any());

        mockMvc.perform(delete(uri))
                .andExpect(status().isNotFound());

        verify(userService).delete(1L);
    }
}