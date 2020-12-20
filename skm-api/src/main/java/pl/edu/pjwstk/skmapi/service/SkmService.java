package pl.edu.pjwstk.skmapi.service;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Train;
import pl.edu.pjwstk.skmapi.model.enums.Station;
import pl.edu.pjwstk.skmapi.repository.TrainRepository;
import pl.edu.pjwstk.skmapi.utils.PersonGenerator;
import pl.edu.pjwstk.skmapi.utils.Randomizer;

import java.util.Set;

@Service
public class SkmService {
    private final TrainRepository trainRepository;

    public SkmService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public void moveSimulationStepForward() {
        var trains = trainRepository.findAll();
        for (var train : trains) {
            moveTrain(train);
        }
        trainRepository.saveAll(trains);
    }


    private void moveTrain(Train train) {
        if (train.getCurrentStation().isLastStation()) {
            waitOnLastStation(train);
        } else {
            makeMoveToNextStation(train);
        }
    }

    private void waitOnLastStation(Train train) {
        if (train.getWaitedTimeOnLastStation() >= train.getCurrentStation().getPauseTime()) {
            train.setWaitedTimeOnLastStation(0);
            changeDirection(train);
            makeMoveToNextStation(train);
        } else {
            train.setWaitedTimeOnLastStation(train.getWaitedTimeOnLastStation() + 1);
        }
    }

    private void changeDirection(Train train) {
        if (train.getCurrentStation().getId() == 0) {
            train.setDirection(1);
        } else {
            train.setDirection(-1);
        }
    }

    private void makeMoveToNextStation(Train train) {
        var currentStation = train.getCurrentStation();
        int direction = train.getDirection();

        getNewPeopleOnBoard(train);
        train.setCurrentStation(Station.values()[currentStation.getId() + direction]);
        removePeopleFromTrain(train);
    }

    private void getNewPeopleOnBoard(Train train) {
        int newPeopleNumber = Math.min(Randomizer.getRandomNumberInRange(2, 8), train.getNumberOfFreePlacesInTrain());
        PersonGenerator personGenerator = new PersonGenerator();
        for (int i = 0; i < newPeopleNumber; i++) {
            Compartment notFullCompartment = getRandomNotFullCompartment(train.getCompartments());
            var person = personGenerator.generate(train.getCurrentStation(), train.getDirection(), notFullCompartment);
            notFullCompartment.addPersonOnBoard(person);
        }
    }

    private Compartment getRandomNotFullCompartment(Set<Compartment> compartments) {
        Compartment[] notFullCompartments = compartments.stream()
                .filter(compartment -> !compartment.isFull())
                .toArray(Compartment[]::new);
        return (Compartment) Randomizer.getRandomElementFromArray(notFullCompartments);
    }

    private void removePeopleFromTrain(Train train) {
        train.getCompartments().forEach(compartment -> {
            var peopleOnBoard = compartment.getPeopleOnBoard();
            peopleOnBoard.removeIf(person -> person.getDestinationStation().getId() == train.getCurrentStation().getId());
            compartment.setPeopleOnBoard(peopleOnBoard);
        });
    }
}
