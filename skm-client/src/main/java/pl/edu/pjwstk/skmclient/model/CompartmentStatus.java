package pl.edu.pjwstk.skmclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CompartmentStatus {

    private final int id;
    private final List<String> peopleNames;

    @JsonCreator
    public CompartmentStatus(@JsonProperty("id") int id,
                             @JsonProperty("peopleNames") List<String> peopleNames) {
        this.id = id;
        this.peopleNames = peopleNames;
    }

    public int getID() {
        return id;
    }

    public List<String> getPeopleNames() {
        return peopleNames;
    }
}