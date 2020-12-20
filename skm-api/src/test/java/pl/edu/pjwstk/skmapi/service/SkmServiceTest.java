package pl.edu.pjwstk.skmapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Person;
import pl.edu.pjwstk.skmapi.model.Train;
import pl.edu.pjwstk.skmapi.model.enums.Station;
import pl.edu.pjwstk.skmapi.repository.TrainRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
class SkmServiceTest {

    @Mock
    TrainRepository trainRepository;

    @InjectMocks
    SkmService skmService;

    @Test
    void moveSimulationStepForwardMovesTrains() {
        Compartment compartment1 = new Compartment();
        compartment1.setCapacity(5);
        Compartment compartment2 = new Compartment();
        compartment2.setCapacity(5);

        Train train1 = new Train();
        train1.setDirection(-1);
        train1.setCurrentStation(Station.GDANSK_GLOWNY);
        train1.setWaitedTimeOnLastStation(2);
        train1.setCompartments(Set.of(compartment1));

        Train train2 = new Train();
        train2.setDirection(1);
        train2.setCurrentStation(Station.GDYNIA_GLOWNA);
        train2.setWaitedTimeOnLastStation(2);
        train2.setCompartments(Set.of(compartment2));

        List<Train> trainsList = List.of(train1, train2);

        when(trainRepository.findAll()).thenReturn(trainsList);

        for (int i = 0; i < 15; i++) {
            skmService.moveSimulationStepForward();
        }

        verify(trainRepository, times(15)).saveAll(anyList());

        assertEquals(Station.GDYNIA_GLOWNA, train1.getCurrentStation());
        assertEquals(Station.GDANSK_GLOWNY, train2.getCurrentStation());
    }

    @Test
    void moveSimulationStepForwardHoldsTrainsOnLastStationAndChangesTheirDirection() {
        Compartment compartment1 = new Compartment();
        compartment1.setCapacity(5);
        Compartment compartment2 = new Compartment();
        compartment2.setCapacity(5);

        Train train1 = new Train();
        train1.setDirection(1);
        train1.setCurrentStation(Station.GDANSK_POLITECHNIKA);
        train1.setWaitedTimeOnLastStation(0);
        train1.setCompartments(Set.of(compartment1));

        Train train2 = new Train();
        train2.setDirection(-1);
        train2.setCurrentStation(Station.GDYNIA_REDLOWO);
        train2.setWaitedTimeOnLastStation(0);
        train2.setCompartments(Set.of(compartment2));

        List<Train> trainsList = List.of(train1, train2);

        when(trainRepository.findAll()).thenReturn(trainsList);

        for (int i = 0; i < 5; i++) {
            skmService.moveSimulationStepForward();
        }

        verify(trainRepository, times(5)).saveAll(anyList());

        assertEquals(Station.GDANSK_STOCZNIA, train1.getCurrentStation());
        assertEquals(-1, train1.getDirection());
        assertEquals(Station.GDYNIA_WZGORZE_SW_MAKSYM, train2.getCurrentStation());
        assertEquals(1, train2.getDirection());
    }

    @Test
    void moveSimulationStepForwardAddsPassengers() {
        Compartment compartment = new Compartment();
        compartment.setCapacity(5);
        compartment.setPeopleOnBoard(new HashSet<>());

        Train train = new Train();
        train.setDirection(-1);
        train.setCurrentStation(Station.GDANSK_GLOWNY);
        train.setWaitedTimeOnLastStation(2);
        train.setCompartments(Set.of(compartment));

        when(trainRepository.findAll()).thenReturn(List.of(train));

        skmService.moveSimulationStepForward();

        verify(trainRepository).saveAll(anyList());

        assertTrue(compartment.getPeopleOnBoard().size() > 0);
    }

    @Test
    void moveSimulationStepForwardDoesNotAddPassengersToFullCompartment() {

        Compartment compartment = spy(new Compartment());
        compartment.setCapacity(3);

        Set<Person> people = new HashSet<>();
        people.add(new Person("foo", Station.GDYNIA_GLOWNA, compartment));
        people.add(new Person("foo", Station.GDYNIA_GLOWNA, compartment));
        people.add(new Person("foo", Station.GDYNIA_GLOWNA, compartment));
        compartment.setPeopleOnBoard(people);

        Train train = new Train();
        train.setDirection(-1);
        train.setCurrentStation(Station.GDANSK_GLOWNY);
        train.setWaitedTimeOnLastStation(2);
        train.setCompartments(Set.of(compartment));

        when(trainRepository.findAll()).thenReturn(List.of(train));

        skmService.moveSimulationStepForward();

        verify(trainRepository).saveAll(anyList());

        verify(compartment, never()).addPersonOnBoard(any());
    }

    @Test
    void moveSimulationStepForwardRemovesPassengersOnDestinationStation() {
        Compartment compartment = new Compartment();
        compartment.setCapacity(5);

        Person person = new Person("foo", Station.GDANSK_STOCZNIA, compartment);
        Set<Person> people = new HashSet<>();
        people.add(person);
        compartment.setPeopleOnBoard(people);

        Train train = new Train();
        train.setDirection(-1);
        train.setCurrentStation(Station.GDANSK_GLOWNY);
        train.setWaitedTimeOnLastStation(2);
        train.setCompartments(Set.of(compartment));

        when(trainRepository.findAll()).thenReturn(List.of(train));

        skmService.moveSimulationStepForward();

        verify(trainRepository).saveAll(anyList());

        assertFalse(compartment.getPeopleOnBoard().contains(person));
    }
}