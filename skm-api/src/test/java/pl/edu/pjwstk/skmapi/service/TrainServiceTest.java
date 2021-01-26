package pl.edu.pjwstk.skmapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjwstk.skmapi.model.Train;
import pl.edu.pjwstk.skmapi.model.enums.Station;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.repository.TrainRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrainServiceTest {

    @Mock
    TrainRepository trainRepository;

    @Mock
    CompartmentRepository compartmentRepository;

    @InjectMocks
    TrainService trainService;


    @Test
    public void getAllShouldReturnListOfTrains() {
        Train train1 = new Train();
        train1.setId(1L);
        Train train2 = new Train();
        train2.setId(2L);

        when(trainRepository.findAll()).thenReturn(List.of(train1, train2));

        var result = trainService.getAll();

        verify(trainRepository).findAll();

        assertEquals(train1.getId(), result.get(0).getId());
        assertEquals(train2.getId(), result.get(1).getId());
    }

    @Test
    public void getAllShouldReturnEmptyListOfTrainsIfRepositoryIsEmpty() {
        when(trainRepository.findAll()).thenReturn(List.of());

        var result = trainService.getAll();

        verify(trainRepository).findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getByIdShouldReturnTrainWithGivenId() {
        Train train = new Train();
        Long id = 1L;
        train.setId(id);

        when(trainRepository.findById(id)).thenReturn(Optional.of(train));

        var result = trainService.getById(id);

        verify(trainRepository).findById(id);

        assertEquals(train.getId(), result.getId());
    }

    @Test
    public void getByIdShouldThrowEntityNotFoundExceptionWhenTrainIsNotPresent() {
        Long id = 1L;

        when(trainRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            trainService.getById(id);
        });

        verify(trainRepository).findById(id);
    }

    @Test
    public void deleteShouldCallRepositoryDeleteWhenValidIdIsGiven() {
        Train train = new Train();
        Long id = 1L;
        train.setId(id);

        when(trainRepository.findById(id)).thenReturn(Optional.of(train));

        trainService.delete(id);

        verify(trainRepository).delete(train);
    }

    @Test
    public void deleteShouldThrowNotFoundExceptionWhenTrainWithGivenIdDoesNotExist() {
        Long id = 1L;

        when(trainRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                trainService.delete(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void createOrUpdateSavesNewTrainToDatabase() {
        Train train = new Train();
        train.setCurrentStation(Station.GDANSK_GLOWNY);
        train.setDirection(1);
        train.setWaitedTimeOnLastStation(0);
        Train trainWithId = new Train();
        trainWithId.setId(1L);
        trainWithId.setCurrentStation(Station.GDANSK_GLOWNY);
        trainWithId.setDirection(1);
        trainWithId.setWaitedTimeOnLastStation(0);

        when(trainRepository.save(train)).thenReturn(trainWithId);

        var result = trainService.createOrUpdate(train);

        verify(trainRepository).save(train);

        assertEquals(train.getCurrentStation(), result.getCurrentStation());
        assertEquals(train.getDirection(), result.getDirection());
        assertEquals(train.getWaitedTimeOnLastStation(), result.getWaitedTimeOnLastStation());
        assertEquals(result.getId(), 1L);
    }

    @Test
    public void createOrUpdateUpdatesExistingTrain() {
        Train trainToUpdate = new Train();
        trainToUpdate.setId(1L);
        trainToUpdate.setCurrentStation(Station.GDANSK_GLOWNY);
        trainToUpdate.setDirection(1);
        trainToUpdate.setWaitedTimeOnLastStation(2);

        Train trainFromDatabase = new Train();
        trainFromDatabase.setId(1L);
        trainFromDatabase.setCurrentStation(Station.GDANSK_OLIWA);
        trainFromDatabase.setDirection(-1);
        trainFromDatabase.setWaitedTimeOnLastStation(0);

        when(trainRepository.findById(trainToUpdate.getId())).thenReturn(Optional.of(trainFromDatabase));
        when(trainRepository.save(any(Train.class))).then(AdditionalAnswers.returnsFirstArg());

        var result = trainService.createOrUpdate(trainToUpdate);

        verify(trainRepository).save(any(Train.class));

        assertEquals(trainToUpdate.getId(), result.getId());
        assertEquals(trainToUpdate.getCurrentStation(), result.getCurrentStation());
        assertEquals(trainToUpdate.getDirection(), result.getDirection());
        assertEquals(trainToUpdate.getWaitedTimeOnLastStation(), result.getWaitedTimeOnLastStation());
    }

    @Test
    public void createOrUpdateTrainShouldThrowEntityNotFoundExceptionIfItHasIdAndIsNotPresentInDatabase() {
        Train train = new Train();
        train.setId(1L);
        train.setDirection(1);
        train.setCurrentStation(Station.GDANSK_STOCZNIA);
        train.setWaitedTimeOnLastStation(0);

        when(trainRepository.findById(train.getId())).thenReturn(Optional.empty());
        when(trainRepository.save(any(Train.class))).then(AdditionalAnswers.returnsFirstArg());

        assertThrows(EntityNotFoundException.class, () -> {
            trainService.createOrUpdate(train);
        });
    }
}