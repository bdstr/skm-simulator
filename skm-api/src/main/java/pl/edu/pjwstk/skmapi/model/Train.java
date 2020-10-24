package pl.edu.pjwstk.skmapi.model;

import java.util.List;
import java.util.Random;

public class Train {

    private static final int LAST_STATION_WAIT_TIME = 2;

    private final int id;
    private final int compartmentNumber;
    private final int compartmentPlacesNumber;
    private TrainStation currentTraintStation;
    private int movingDirection;
    private int waitedTimeOnLastStation;

    private List<Person> peopleOnBoard;

    public Train(int id, int compartmentNumber, int compartmentPlacesNumber) {
        this.id = id;
        this.compartmentNumber = compartmentNumber;
        this.compartmentPlacesNumber = compartmentPlacesNumber;

        Random rand = new Random();
        this.currentTraintStation = TrainStation.values()[rand.nextInt(TrainStation.values().length)];
        this.movingDirection = rand.nextBoolean() ? 1 : -1;
        this.waitedTimeOnLastStation = 0;
    }

    public int getId() {
        return id;
    }

    public int getCompartmentNumber() {
        return compartmentNumber;
    }

    public int getCompartmentPlacesNumber() {
        return compartmentPlacesNumber;
    }

    public TrainStation getCurrentTraintStation() {
        return currentTraintStation;
    }

    public void moveForward() {
        if (currentTraintStation.isLastStation()) {
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
        if (currentTraintStation.getId() == 0) {
            movingDirection = 1;
        } else {
            movingDirection = -1;
        }
    }

    private void makeMove() {
        int currentStationID = currentTraintStation.getId();
        currentTraintStation = TrainStation.values()[currentStationID + movingDirection];
    }

}

