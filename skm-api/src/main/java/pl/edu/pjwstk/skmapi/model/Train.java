package pl.edu.pjwstk.skmapi.model;

import java.util.*;

public class Train {

    private static final int LAST_STATION_WAIT_TIME = 2;

    private final int id;
    private final int compartmentsNumber;
    private final int compartmentPlacesNumber;
    private final List<Person> peopleOnBoard;
    private TrainStation currentTrainStation;
    private final Direction direction;
    private int waitedTimeOnLastStation;

    public Train(int id, int compartmentsNumber, int compartmentPlacesNumber) {
        this.id = id;
        this.compartmentsNumber = compartmentsNumber;
        this.compartmentPlacesNumber = compartmentPlacesNumber;
        peopleOnBoard = new ArrayList<>();
        Random random = new Random();
        this.currentTrainStation = TrainStation.values()[random.nextInt(TrainStation.values().length)];
        this.direction = new Direction();
        this.waitedTimeOnLastStation = 0;
    }

    public int getID() {
        return id;
    }

    public List<Person> getPeopleOnBoard() {
        return peopleOnBoard;
    }

    public TrainStation getCurrentTrainStation() {
        return currentTrainStation;
    }

    public double getPercentageOfTrainFilling() {
        return (100.0 / calculateTotalPlacesInTrain()) * peopleOnBoard.size();
    }

    private int calculateTotalPlacesInTrain() {
        return compartmentsNumber * compartmentPlacesNumber;
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
        getPeopleOffTheTrain();
    }

    private void getNewPeopleOnBoard() {
        int freePlacesInTrain = calculateFreePlacesInTrain();
        Random rand = new Random();
        int newPeopleNumber = Math.min(rand.nextInt(7) + 2, freePlacesInTrain);
        for (int i = 0; i < newPeopleNumber; i++) {
            peopleOnBoard.add(new Person(currentTrainStation, direction));
        }
    }

    private int calculateFreePlacesInTrain() {
        return calculateTotalPlacesInTrain() - peopleOnBoard.size();
    }

    private void getPeopleOffTheTrain() {
        peopleOnBoard.removeIf(person -> person.getDestinationStation().getId() == currentTrainStation.getId());
    }

}

