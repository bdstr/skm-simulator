package pl.edu.pjwstk.skmapi.model;

import java.util.List;

public class CompartmentStatus {

    private final int id;
    private final List<String> peopleNames;

    public CompartmentStatus(int id, List<String> peopleNames) {
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
