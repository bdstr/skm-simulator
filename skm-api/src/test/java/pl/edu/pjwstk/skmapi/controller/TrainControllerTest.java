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
import pl.edu.pjwstk.skmapi.model.Train;
import pl.edu.pjwstk.skmapi.model.enums.Station;
import pl.edu.pjwstk.skmapi.service.TrainService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = TrainController.class)
class TrainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TrainService trainService;

    @Captor
    ArgumentCaptor<Train> argumentCaptor;

    @Test
    public void getAllShouldReturnListOfTrains() throws Exception {
        String uri = "/trains";
        Train train1 = new Train();
        train1.setId(1L);
        train1.setCurrentStation(Station.GDANSK_GLOWNY);
        train1.setDirection(-1);
        train1.setWaitedTimeOnLastStation(2);

        Train train2 = new Train();
        train2.setId(2L);
        train2.setCurrentStation(Station.GDYNIA_GLOWNA);
        train2.setDirection(1);
        train2.setWaitedTimeOnLastStation(2);

        when(trainService.getAll()).thenReturn(List.of(train1, train2));

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(train1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].current_station").value(train1.getCurrentStation().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].direction").value(train1.getDirection()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].waited_time_on_last_station").value(train1.getWaitedTimeOnLastStation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(train2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].current_station").value(train2.getCurrentStation().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].direction").value(train2.getDirection()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].waited_time_on_last_station").value(train2.getWaitedTimeOnLastStation()));

        verify(trainService).getAll();
    }

    @Test
    public void getByIdShouldReturnTrainWithGivenId() throws Exception {
        String uri = "/trains/1";
        long id = 1L;
        Train train = new Train();
        train.setId(id);
        train.setCurrentStation(Station.GDANSK_GLOWNY);
        train.setDirection(-1);
        train.setWaitedTimeOnLastStation(2);

        when(trainService.getById(id)).thenReturn(train);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(train.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current_station").value(train.getCurrentStation().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direction").value(train.getDirection()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.waited_time_on_last_station").value(train.getWaitedTimeOnLastStation()));

        verify(trainService).getById(id);
    }

    @Test
    public void updateShouldCallCreateOrUpdateMethod() throws Exception {
        String uri = "/trains";

        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "id": 1,
                    "currentStation": "SOPOT",
                    "direction": -1,
                    "waitedTimeOnLastStation": 2
                }"""))
                .andExpect(status().isAccepted());

        verify(trainService).createOrUpdate(argumentCaptor.capture());

        assertEquals(1L, argumentCaptor.getValue().getId());
        assertEquals("SOPOT", argumentCaptor.getValue().getCurrentStation().name());
        assertEquals(-1, argumentCaptor.getValue().getDirection());
        assertEquals(2, argumentCaptor.getValue().getWaitedTimeOnLastStation());
    }

    @Test
    public void addShouldCallCreateOrUpdateMethod() throws Exception {
        String uri = "/trains";

        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "direction": 1,
                    "currentStation": "GDANSK_OLIWA",
                    "waitedTimeOnLastStation": 0
                }"""))
                .andExpect(status().isAccepted());

        verify(trainService).createOrUpdate(argumentCaptor.capture());

        assertEquals("GDANSK_OLIWA", argumentCaptor.getValue().getCurrentStation().name());
        assertEquals(1, argumentCaptor.getValue().getDirection());
        assertEquals(0, argumentCaptor.getValue().getWaitedTimeOnLastStation());
    }

    @Test
    public void deleteShouldReturnStatusAcceptedWhenTrainIsPresentInDatabase() throws Exception {
        String uri = "/trains/1";

        mockMvc.perform(delete(uri))
                .andExpect(status().isAccepted());

        verify(trainService).delete(1L);
    }

    @Test
    public void deleteShouldReturnStatusNotFoundWhenTrainIsNotPresentInDatabase() throws Exception {
        String uri = "/trains/1";

        doThrow(EntityNotFoundException.class).when(trainService).delete(any());

        mockMvc.perform(delete(uri))
                .andExpect(status().isNotFound());

        verify(trainService).delete(1L);
    }

}