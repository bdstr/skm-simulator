package pl.edu.pjwstk.skmapi.security;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsernameShouldReturnUserDetails() {
        String username = "user";

        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("password");
        user.setAuthorities("ROLE_USER");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        var result = userDetailsService.loadUserByUsername(username);

        verify(userRepository).findByUsername(username);

        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getAuthorities(), result.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));
    }

    @Test
    void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserNotPresentInDatabase() {
        String username = "user";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        verify(userRepository).findByUsername(username);

    }

}