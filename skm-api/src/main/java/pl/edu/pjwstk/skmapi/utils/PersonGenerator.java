package pl.edu.pjwstk.skmapi.utils;

import com.github.javafaker.Faker;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Person;
import pl.edu.pjwstk.skmapi.model.enums.Station;

import java.util.Locale;

public class PersonGenerator {

    public Person generate(Station startingStation, int direction, Compartment compartment) {
        String name = generateRandomName();
        int randomStationNumber;
        if (direction == -1) {
            randomStationNumber = Randomizer.getRandomNumberInRange(0, startingStation.getId() - 1);
        } else {
            randomStationNumber = Randomizer.getRandomNumberInRange(startingStation.getId() + 1, Station.values().length - 1);
        }
        Station destinationStation = Station.values()[randomStationNumber];

        return new Person(name, destinationStation, compartment);
    }

    private String generateRandomName() {
        Faker faker = new Faker(new Locale("pl"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return firstName + " " + lastName;
    }
}
