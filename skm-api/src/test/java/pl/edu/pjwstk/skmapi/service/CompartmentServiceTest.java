package pl.edu.pjwstk.skmapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CompartmentServiceTest {

    @Mock
    CompartmentRepository compartmentRepository;

    @InjectMocks
    CompartmentService compartmentService;


    @Test
    public void getAllShouldReturnListOfCompartments() {
        Compartment compartment1 = new Compartment();
        compartment1.setId(1L);
        Compartment compartment2 = new Compartment();
        compartment2.setId(2L);

        when(compartmentRepository.findAll()).thenReturn(List.of(compartment1, compartment2));

        var result = compartmentService.getAll();

        verify(compartmentRepository).findAll();

        assertEquals(compartment1.getId(), result.get(0).getId());
        assertEquals(compartment2.getId(), result.get(1).getId());
    }

    @Test
    public void getAllShouldReturnEmptyListOfCompartmentsIfRepositoryIsEmpty() {
        when(compartmentRepository.findAll()).thenReturn(List.of());

        var result = compartmentService.getAll();

        verify(compartmentRepository).findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getByIdShouldReturnCompartmentWithGivenId() {
        Long id = 1L;
        Compartment compartment = new Compartment();
        compartment.setId(id);

        when(compartmentRepository.findById(id)).thenReturn(Optional.of(compartment));

        var result = compartmentService.getById(id);

        verify(compartmentRepository).findById(id);

        assertEquals(compartment.getId(), result.getId());
    }

    @Test
    public void getByIdShouldReturnNullWhenCompartmentIsNotPresent() {
        Long id = 1L;

        when(compartmentRepository.findById(id)).thenReturn(Optional.empty());

        var result = compartmentService.getById(id);

        verify(compartmentRepository).findById(id);

        assertNull(result);
    }

    @Test
    public void deleteShouldCallRepositoryDeleteWhenValidIdIsGiven() {
        Long id = 1L;
        Compartment compartment = new Compartment();
        compartment.setId(id);

        when(compartmentRepository.findById(id)).thenReturn(Optional.of(compartment));

        compartmentService.delete(id);

        verify(compartmentRepository).delete(compartment);
    }

    @Test
    public void deleteShouldThrowNotFoundExceptionWhenCompartmentWithGivenIdDoesNotExist() {
        Long id = 1L;

        when(compartmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                compartmentService.delete(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void createOrUpdateSavesNewCompartmentToDatabase() {
        Compartment compartment = new Compartment();
        compartment.setCapacity(20);
        Compartment compartmentWithId = new Compartment();
        compartmentWithId.setId(1L);
        compartmentWithId.setCapacity(20);

        when(compartmentRepository.save(compartment)).thenReturn(compartmentWithId);

        var result = compartmentService.createOrUpdate(compartment);

        verify(compartmentRepository).save(compartment);

        assertEquals(compartment.getCapacity(), result.getCapacity());
        assertEquals(result.getId(), 1L);
    }

    @Test
    public void createOrUpdateUpdatesExistingCompartment() {
        Compartment compartmentToUpdate = new Compartment();
        compartmentToUpdate.setId(1L);
        compartmentToUpdate.setCapacity(20);
        Compartment compartmentFromDatabase = new Compartment();
        compartmentFromDatabase.setId(1L);
        compartmentFromDatabase.setCapacity(10);

        when(compartmentRepository.findById(compartmentToUpdate.getId())).thenReturn(Optional.of(compartmentFromDatabase));
        when(compartmentRepository.save(any(Compartment.class))).then(AdditionalAnswers.returnsFirstArg());

        var result = compartmentService.createOrUpdate(compartmentToUpdate);

        verify(compartmentRepository).save(any(Compartment.class));

        assertEquals(compartmentToUpdate.getId(), result.getId());
        assertEquals(compartmentToUpdate.getCapacity(), result.getCapacity());
    }

    @Test
    public void createOrUpdateInsertsCompartmentIfItHasIdAndIsNotPresentInDatabase() {
        Compartment compartment = new Compartment();
        compartment.setId(1L);
        compartment.setCapacity(20);

        when(compartmentRepository.findById(compartment.getId())).thenReturn(Optional.empty());
        when(compartmentRepository.save(any(Compartment.class))).then(AdditionalAnswers.returnsFirstArg());

        var result = compartmentService.createOrUpdate(compartment);

        assertEquals(compartment.getId(), result.getId());
        assertEquals(compartment.getCapacity(), result.getCapacity());
    }
}