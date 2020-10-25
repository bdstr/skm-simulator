package pl.edu.pjwstk.skmapi.model;

import java.util.Random;

public class Person {

    private final String name;
    private final TrainStation destinationStation;

    public Person(TrainStation startingStation, Direction direction) {
        name = "Will Smith";

        int randomStationNumber;
        if (direction.getDirection() == Direction.START_TO_END) {
            randomStationNumber = getRandomNumberInRange(startingStation.getId() + 1, TrainStation.values().length - 1);
        } else {
            randomStationNumber = getRandomNumberInRange(0, startingStation.getId() - 1);
        }
        destinationStation = TrainStation.values()[randomStationNumber];
    }

    public String getName() {
        return name;
    }

    public TrainStation getDestinationStation() {
        return destinationStation;
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            return max;
        }
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
