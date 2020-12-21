package pl.edu.pjwstk.skmapi.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Train;
import pl.edu.pjwstk.skmapi.service.CompartmentService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CompartmentController.class)
class CompartmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CompartmentService compartmentService;

    @Captor
    ArgumentCaptor<Compartment> argumentCaptor;

    @Test
    void getAllShouldReturnListOfCompartments() throws Exception {
        String uri = "/compartments";
        Train train = new Train();
        train.setId(1L);

        Compartment compartment1 = new Compartment();
        compartment1.setId(1L);
        compartment1.setCapacity(10);
        compartment1.setTrain(train);

        Compartment compartment2 = new Compartment();
        compartment2.setId(2L);
        compartment2.setCapacity(20);
        compartment2.setTrain(train);

        when(compartmentService.getAll()).thenReturn(List.of(compartment1, compartment2));

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(compartment1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].train_id").value(compartment1.getTrain().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity").value(compartment1.getCapacity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(compartment2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].train_id").value(compartment2.getTrain().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].capacity").value(compartment2.getCapacity()));

        verify(compartmentService).getAll();
    }

    @Test
    public void getByIdShouldReturnCompartmentWithGivenId() throws Exception {
        String uri = "/compartments/1";
        long id = 1L;

        Train train = new Train();
        train.setId(id);

        Compartment compartment = new Compartment();
        compartment.setId(id);
        compartment.setCapacity(10);
        compartment.setTrain(train);

        when(compartmentService.getById(id)).thenReturn(compartment);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(compartment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(compartment.getCapacity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.train_id").value(compartment.getTrain().getId()));

        verify(compartmentService).getById(id);
    }

    @Test
    public void updateShouldCallCreateOrUpdateMethod() throws Exception {
        String uri = "/compartments";

        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "id": 1,
                    "capacity": 20,
                    "train": {
                        "id": 1
                    }
                }"""))
                .andExpect(status().isAccepted());

        verify(compartmentService).createOrUpdate(argumentCaptor.capture());

        assertEquals(1L, argumentCaptor.getValue().getId());
        assertEquals(20, argumentCaptor.getValue().getCapacity());
        assertEquals(1L, argumentCaptor.getValue().getTrain().getId());
    }

    @Test
    public void addShouldCallCreateOrUpdateMethod() throws Exception {
        String uri = "/compartments";

        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "capacity": 20,
                    "train": {
                        "id": 1
                    }
                }"""))
                .andExpect(status().isAccepted());

        verify(compartmentService).createOrUpdate(argumentCaptor.capture());

        assertEquals(20, argumentCaptor.getValue().getCapacity());
        assertEquals(1L, argumentCaptor.getValue().getTrain().getId());
    }

    @Test
    public void deleteShouldReturnStatusAcceptedWhenCompartmentIsPresentInDatabase() throws Exception {
        String uri = "/compartments/1";

        mockMvc.perform(delete(uri))
                .andExpect(status().isAccepted());

        verify(compartmentService).delete(1L);
    }

    @Test
    public void deleteShouldReturnStatusNotFoundWhenCompartmentIsNotPresentInDatabase() throws Exception {
        String uri = "/compartments/1";

        doThrow(EntityNotFoundException.class).when(compartmentService).delete(any());

        mockMvc.perform(delete(uri))
                .andExpect(status().isNotFound());

        verify(compartmentService).delete(1L);
    }
}