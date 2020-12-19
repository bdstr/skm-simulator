package pl.edu.pjwstk.skmapi.service;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Person;
import pl.edu.pjwstk.skmapi.model.Station;
import pl.edu.pjwstk.skmapi.repository.PersonRepository;
import pl.edu.pjwstk.skmapi.utils.Randomizer;

import java.util.Locale;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person create(Compartment compartment, Station startingStation, int direction) {
        int randomStationNumber;
        if (direction == -1) {
            randomStationNumber = Randomizer.getRandomNumberInRange(0, startingStation.getId() - 1);
        } else {
            randomStationNumber = Randomizer.getRandomNumberInRange(startingStation.getId() + 1, Station.values().length - 1);
        }

        var person = new Person();
        person.setName(generateRandomName());
        person.setDestinationStation(Station.values()[randomStationNumber]);
        person.setCompartment(compartment);

        return repository.save(person);
    }

    private String generateRandomName() {
        Faker faker = new Faker(new Locale("pl"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return firstName + " " + lastName;
    }
}
