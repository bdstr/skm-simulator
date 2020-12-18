package pl.edu.pjwstk.skmapi.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.Compartment;

import java.util.Optional;

import static pl.edu.pjwstk.skmapi.utils.Utils.fallbackIfNull;

@Service
public class CompartmentService extends CrudService<Compartment> {

    public CompartmentService(JpaRepository<Compartment, Long> repository) {
        super(repository);
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
            dbEntity.setPeopleOnBoard(fallbackIfNull(updateEntity.getPeopleOnBoard(), dbEntity.getPeopleOnBoard()));

            return repository.save(dbEntity);
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }
}
