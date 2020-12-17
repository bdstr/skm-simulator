package pl.edu.pjwstk.skmapi.model.statuses;

import java.util.List;

public class CompartmentStatus {

    private final Long id;
    private final List<String> peopleNames;

    public CompartmentStatus(Long id, List<String> peopleNames) {
        this.id = id;
        this.peopleNames = peopleNames;
    }

    public Long getID() {
        return id;
    }

    public List<String> getPeopleNames() {
        return peopleNames;
    }
}
