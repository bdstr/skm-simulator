package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/users")
public class UserController extends CrudController<User> {

    @Autowired
    public UserController(UserService userService) {
        super(userService);
    }

    @Override
    public Function<User, Map<String, Object>> transformToDTO() {
        return user -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", user.getId());
            payload.put("username", user.getUsername());
            payload.put("password", user.getPassword());
            payload.put("authorities", user.getAuthorities());
            return payload;
        };
    }
}
