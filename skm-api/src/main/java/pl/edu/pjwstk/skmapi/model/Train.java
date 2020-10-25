package pl.edu.pjwstk.skmapi.model;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Train {

    private static final int LAST_STATION_WAIT_TIME = 2;

    private final int id;
    private TrainStation currentTrainStation;
    private final Direction direction;
    private int waitedTimeOnLastStation;

    private final List<Person> peopleOnBoard;

    @Value("${configuration.compartmentsNumber}")
    private int compartmentsNumber;

    @Value("${configuration.compartmentPlacesNumber}")
    private int compartmentPlacesNumber;

    public Train(int id) {
        this.id = id;
        peopleOnBoard = new ArrayList<>();
        Random random = new Random();
        this.currentTrainStation = TrainStation.values()[random.nextInt(TrainStation.values().length)];
        this.direction = new Direction();
        this.waitedTimeOnLastStation = 0;
    }

    public int getId() {
        return id;
    }

    public int getCompartmentsNumber() {
        return compartmentsNumber;
    }

    public int getCompartmentPlacesNumber() {
        return compartmentPlacesNumber;
    }

    public TrainStation getCurrentTraintStation() {
        return currentTrainStation;
    }

    public List<Person> getPeopleOnBoard() {
        return peopleOnBoard;
    }

    public void moveForward() {
        if (currentTrainStation.isLastStation()) {
            waitOnLastStation();
        } else {
            makeMove();
        }
    }

    private void waitOnLastStation() {
        if (waitedTimeOnLastStation >= LAST_STATION_WAIT_TIME) {
            waitedTimeOnLastStation = 0;
            changeDirection();
            makeMove();
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

    private void makeMove() {
        getNewPeopleOnBoard();
        currentTrainStation = TrainStation.values()[currentTrainStation.getId() + direction.getDirection()];
        getPeopleOffTheTrain();
    }

    private void getNewPeopleOnBoard() {
        int freePlacesInTrain = calculateFreePlacesInTrain();
        Random rand = new Random();
        int newPeopleNumber = Math.max(rand.nextInt(7) + 2, freePlacesInTrain);
        for (int i = 0; i < newPeopleNumber; i++) {
            peopleOnBoard.add(new Person(currentTrainStation, direction));
        }
    }

    private int calculateFreePlacesInTrain() {
        return compartmentsNumber * compartmentPlacesNumber - peopleOnBoard.size();
    }

    private void getPeopleOffTheTrain() {
        peopleOnBoard.removeIf(person -> person.getDestinationStation().getId() == currentTrainStation.getId());
    }

}

