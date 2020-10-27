package pl.edu.pjwstk.skmapi.model;

public class Person {

    private final String name;
    private final TrainStation destinationStation;

    public Person(TrainStation startingStation, Direction direction) {
        name = "Will Smith";

        int randomStationNumber;
        if (direction.getDirection() == Direction.START_TO_END) {
            randomStationNumber = Randomizer.getRandomNumberInRange(startingStation.getId() + 1, TrainStation.values().length - 1);
        } else {
            randomStationNumber = Randomizer.getRandomNumberInRange(0, startingStation.getId() - 1);
        }
        destinationStation = TrainStation.values()[randomStationNumber];
    }

    public String getName() {
        return name;
    }

    public TrainStation getDestinationStation() {
        return destinationStation;
    }
}
