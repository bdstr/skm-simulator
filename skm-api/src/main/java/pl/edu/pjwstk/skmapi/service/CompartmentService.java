package pl.edu.pjwstk.skmapi.service;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Person;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.repository.PersonRepository;

import java.util.Optional;
import java.util.Set;

import static pl.edu.pjwstk.skmapi.utils.Utils.fallbackIfNull;

@Service
public class CompartmentService extends CrudService<Compartment> {
    private final PersonRepository personRepository;

    public CompartmentService(CompartmentRepository repository, PersonRepository personRepository) {
        super(repository);
        this.personRepository = personRepository;
    }

    @Override
    public Compartment createOrUpdate(Compartment updateEntity) {
        if (updateEntity.getId() == null) {
            return repository.save(updateEntity);
        }

        Optional<Compartment> compartmentInDb = repository.findById(updateEntity.getId());

        if (compartmentInDb.isPresent()) {
            var dbEntity = compartmentInDb.get();

            dbEntity.setTrain(fallbackIfNull(updateEntity.getTrain(), dbEntity.getTrain()));
            dbEntity.setCapacity(fallbackIfNull(updateEntity.getCapacity(), dbEntity.getCapacity()));
            var insertedCompartment = repository.save(dbEntity);

            Set<Person> peopleOnBoard = updateEntity.getPeopleOnBoard();
            peopleOnBoard.forEach(person -> person.setCompartment(dbEntity));
            personRepository.saveAll(peopleOnBoard);
            return insertedCompartment;
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }
}
