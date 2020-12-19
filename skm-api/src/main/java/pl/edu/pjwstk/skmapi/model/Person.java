package pl.edu.pjwstk.skmapi.model;

import com.github.javafaker.Faker;
import pl.edu.pjwstk.skmapi.utils.Randomizer;

import java.util.Locale;

public class Person {

    private final String name;
    private final Station destinationStation;

    public Person(Station startingStation, int direction) {
        name = generateRandomName();
        int randomStationNumber;
        if (direction == -1) {
            randomStationNumber = Randomizer.getRandomNumberInRange(0, startingStation.getId() - 1);
        } else {
            randomStationNumber = Randomizer.getRandomNumberInRange(startingStation.getId() + 1, Station.values().length - 1);
        }
        destinationStation = Station.values()[randomStationNumber];
    }

    public String getName() {
        return name;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    private String generateRandomName() {
        Faker faker = new Faker(new Locale("pl"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return firstName + " " + lastName;
    }
}
