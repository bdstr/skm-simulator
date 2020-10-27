package pl.edu.pjwstk.skmapi.model;

import java.util.*;

public class Train {

    private static final int LAST_STATION_WAIT_TIME = 2;

    private final int id;
    private final int compartmentsNumber;
    private final int compartmentPlacesNumber;
    private TrainStation currentTrainStation;
    private final Direction direction;
    private int waitedTimeOnLastStation;
    private final ArrayList<Compartment> compartments;

    public Train(int id, int compartmentsNumber, int compartmentPlacesNumber) {
        this.id = id;
        this.compartmentsNumber = compartmentsNumber;
        this.compartmentPlacesNumber = compartmentPlacesNumber;
        this.currentTrainStation = (TrainStation) Randomizer.getRandomElementFromArray(TrainStation.values());
        this.direction = new Direction();
        this.waitedTimeOnLastStation = 0;
        this.compartments = new ArrayList<>();
        for (int i = 0; i < compartmentsNumber; i++) {
            compartments.add(new Compartment(i, compartmentPlacesNumber));
        }
    }

    public int getID() {
        return id;
    }

    public ArrayList<Compartment> getCompartmentsList() {
        return compartments;
    }

    public TrainStation getCurrentTrainStation() {
        return currentTrainStation;
    }

    public double getPercentageOfTrainFilling() {
        return (100.0 / getTotalNumberOfPlacesInTrain()) * getNumberOfPeopleOnBoard();
    }

    private int getTotalNumberOfPlacesInTrain() {
        return compartmentsNumber * compartmentPlacesNumber;
    }

    public int getNumberOfPeopleOnBoard() {
        int number = 0;
        for (Compartment compartment : compartments) {
            number += compartment.getNumberOfPeopleOnBoard();
        }
        return number;
    }

    public void moveTrainSimulationStepForward() {
        if (currentTrainStation.isLastStation()) {
            waitOnLastStation();
        } else {
            makeMoveToNextStation();
        }
    }

    private void waitOnLastStation() {
        if (waitedTimeOnLastStation >= LAST_STATION_WAIT_TIME) {
            waitedTimeOnLastStation = 0;
            changeDirection();
            makeMoveToNextStation();
        } else {
            waitedTimeOnLastStation++;
        }
    }

    private void changeDirection() {
        if (currentTrainStation.getId() == 0) {
            direction.setDirection(Direction.START_TO_END);
        } else {
            direction.setDirection(Direction.END_TO_START);
        }
    }

    private void makeMoveToNextStation() {
        getNewPeopleOnBoard();
        currentTrainStation = TrainStation.values()[currentTrainStation.getId() + direction.getDirection()];
        removePeopleFromTrain();
    }

    private void getNewPeopleOnBoard() {
        int newPeopleNumber = Math.min(Randomizer.getRandomNumberInRange(2, 8), getNumberOfFreePlacesInTrain());
        for (int i = 0; i < newPeopleNumber; i++) {
            Compartment notFullCompartment = getRandomNotFullCompartment();
            notFullCompartment.addPersonOnBoard(new Person(currentTrainStation, direction));
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
        for (Compartment compartment : compartments) {
            compartment.removePeopleFromCompartment(currentTrainStation);
        }
    }

}

