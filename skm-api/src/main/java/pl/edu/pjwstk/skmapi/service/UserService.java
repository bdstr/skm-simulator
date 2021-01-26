package pl.edu.pjwstk.skmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static pl.edu.pjwstk.skmapi.utils.Utils.fallbackIfNull;

@Service
public class UserService extends CrudService<User> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public User createOrUpdate(User updateEntity) {
        if (updateEntity.getId() == null && ((UserRepository) repository).findByUsername(updateEntity.getUsername()).isEmpty()) {

            if (updateEntity.getAuthorities() == null || updateEntity.getAuthorities().isBlank()) {
                updateEntity.setAuthorities("ROLE_USER");
            }
            var password = updateEntity.getPassword();
            if (password == null || password.length() < 8) {
                updateEntity.setPassword(passwordEncoder.encode(password));
            } else {
                throw new RuntimeException("Provide strong password!");
            }

            return repository.save(updateEntity);
        }

        Optional<User> userInDb = repository.findById(updateEntity.getId());

        if (userInDb.isPresent()) {
            var dbEntity = userInDb.get();

            if (((UserRepository) repository).findByUsername(updateEntity.getUsername()).isEmpty()) {
                dbEntity.setUsername(fallbackIfNull(updateEntity.getUsername(), dbEntity.getUsername()));
            }
            dbEntity.setPassword(passwordEncoder.encode(
                    fallbackIfNull(updateEntity.getPassword(), dbEntity.getPassword()))
            );
            dbEntity.setAuthorities(fallbackIfNull(updateEntity.getAuthorities(), dbEntity.getAuthorities()));

            return repository.save(dbEntity);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
