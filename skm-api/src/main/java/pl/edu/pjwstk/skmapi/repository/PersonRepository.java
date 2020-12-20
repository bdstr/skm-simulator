package pl.edu.pjwstk.skmapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.skmapi.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
