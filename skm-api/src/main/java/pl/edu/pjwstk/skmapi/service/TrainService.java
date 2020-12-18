package pl.edu.pjwstk.skmapi.service;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Train;
import pl.edu.pjwstk.skmapi.model.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.model.repository.TrainRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static pl.edu.pjwstk.skmapi.utils.Utils.fallbackIfNull;

@Service
public class TrainService extends CrudService<Train> {
    private final CompartmentRepository compartmentRepository;

    public TrainService(TrainRepository repository, CompartmentRepository compartmentRepository) {
        super(repository);
        this.compartmentRepository = compartmentRepository;
    }

    @Override
    public Train createOrUpdate(Train updateEntity) {
        if (updateEntity.getId() == null) {
            var compartments = updateEntity.getCompartments();
            updateEntity.setCompartments(Collections.emptySet());
            Train insertedTrain = repository.save(updateEntity);

            compartments.forEach(compartment -> compartment.setTrain(insertedTrain));
            compartmentRepository.saveAll(compartments);
            return insertedTrain;
        }

        Optional<Train> trainInDb = repository.findById(updateEntity.getId());

        if (trainInDb.isPresent()) {
            var dbEntity = trainInDb.get();

            dbEntity.setCurrentStation(fallbackIfNull(updateEntity.getCurrentStation(), dbEntity.getCurrentStation()));
            dbEntity.setWaitedTimeOnLastStation(fallbackIfNull(updateEntity.getWaitedTimeOnLastStation(), dbEntity.getWaitedTimeOnLastStation()));
            dbEntity.setDirection(fallbackIfNull(updateEntity.getDirection(), dbEntity.getDirection()));
            dbEntity.setCompartments(fallbackIfNull(updateEntity.getCompartments(), dbEntity.getCompartments()));
            var insertedCar = repository.save(dbEntity);

            Set<Compartment> compartments = updateEntity.getCompartments();
            compartments.forEach(compartment -> compartment.setTrain(dbEntity));
            compartmentRepository.saveAll(compartments);

            return insertedCar;
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }

    public void moveSimulationStepForward() {
        var trains = repository.findAll();
        trains.forEach(Train::moveTrainSimulationStepForward);
        repository.saveAll(trains);
    }
}
