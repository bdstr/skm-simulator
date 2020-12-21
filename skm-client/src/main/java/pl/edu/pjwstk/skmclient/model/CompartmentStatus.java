package pl.edu.pjwstk.skmclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CompartmentStatus {

    private final Long id;
    private final int capacity;
    private final Long trainId;
    private final List<String> peopleNames;

    @JsonCreator
    public CompartmentStatus(@JsonProperty("id") Long id,
                             @JsonProperty("capacity") int capacity,
                             @JsonProperty("train_id") Long trainId,
                             @JsonProperty("people_on_board") List<String> peopleNames) {
        this.id = id;
        this.capacity = capacity;
        this.trainId = trainId;
        this.peopleNames = peopleNames;
    }

    public Long getID() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public Long getTrainId() {
        return trainId;
    }

    public List<String> getPeopleNames() {
        return peopleNames;
    }
}
