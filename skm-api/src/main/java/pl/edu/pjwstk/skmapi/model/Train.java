package pl.edu.pjwstk.skmapi.model;

import pl.edu.pjwstk.skmapi.utils.Randomizer;

import javax.persistence.*;
import java.util.ArrayList;

public class Train {

    private final Long id;
    private Station currentStation;
    private final Direction direction;
    private int waitedTimeOnLastStation;
    private final ArrayList<Compartment> compartments;

    public Train(long id, int compartmentsNumber, int compartmentPlacesNumber) {
        this.id = id;
        this.currentStation = (Station) Randomizer.getRandomElementFromArray(Station.values());
        this.direction = new Direction();
        this.waitedTimeOnLastStation = 0;
        this.compartments = new ArrayList<>();
        for (long i = 0L; i < compartmentsNumber; i++) {
            compartments.add(new Compartment(i, compartmentPlacesNumber));
        }
    }

    public long getID() {
        return id;
    }

    public ArrayList<Compartment> getCompartmentsList() {
        return compartments;
    }

    public Station getCurrentTrainStation() {
        return currentStation;
    }

    public double getPercentageOfTrainFilling() {
        return (100.0 / getTotalNumberOfPlacesInTrain()) * getNumberOfPeopleOnBoard();
    }

    private int getTotalNumberOfPlacesInTrain() {
        return compartments.stream()
                .map(Compartment::getCapacity)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public int getNumberOfPeopleOnBoard() {
        return compartments.stream()
                .map(Compartment::getNumberOfPeopleOnBoard)
                .reduce(0, Integer::sum);
    }

    public void moveTrainSimulationStepForward() {
        if (currentStation.isLastStation()) {
            waitOnLastStation(currentStation.getPauseTime());
        } else {
            makeMoveToNextStation();
        }
    }

    private void waitOnLastStation(int pauseTime) {
        if (waitedTimeOnLastStation >= pauseTime) {
            waitedTimeOnLastStation = 0;
            changeDirection();
            makeMoveToNextStation();
        } else {
            waitedTimeOnLastStation++;
        }
    }

    private void changeDirection() {
        if (currentStation.getId() == 0) {
            direction.setDirection(Direction.START_TO_END);
        } else {
            direction.setDirection(Direction.END_TO_START);
        }
    }

    private void makeMoveToNextStation() {
        getNewPeopleOnBoard();
        currentStation = Station.values()[currentStation.getId() + direction.getDirection()];
        removePeopleFromTrain();
    }

    private void getNewPeopleOnBoard() {
        int newPeopleNumber = Math.min(Randomizer.getRandomNumberInRange(2, 8), getNumberOfFreePlacesInTrain());
        for (int i = 0; i < newPeopleNumber; i++) {
            Compartment notFullCompartment = getRandomNotFullCompartment();
            notFullCompartment.addPersonOnBoard(new Person(currentStation, direction));
        }
    }

    private Compartment getRandomNotFullCompartment() {
        Compartment[] notFullCompartments = compartments.stream()
                .filter(compartment -> !compartment.isFull())
                .toArray(Compartment[]::new);
        return (Compartment) Randomizer.getRandomElementFromArray(notFullCompartments);
    }

    private int getNumberOfFreePlacesInTrain() {
        return getTotalNumberOfPlacesInTrain() - getNumberOfPeopleOnBoard();
    }

    private void removePeopleFromTrain() {
        compartments.forEach(compartment -> compartment.removePeopleFromCompartment(currentStation));
    }

}

