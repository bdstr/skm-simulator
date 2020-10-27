package pl.edu.pjwstk.skmapi.model;

import java.util.LinkedHashMap;

public class CompartmentStatus implements Status {

    private final Compartment compartment;

    public CompartmentStatus(Compartment compartment) {
        this.compartment = compartment;
    }

    public LinkedHashMap<String, Object> getStatus() {
        LinkedHashMap<String, Object> status = new LinkedHashMap<>();
        status.put("ID", compartment.getID());
        status.put("People", compartment.getPeopleOnBoardNames());
        return status;
    }
}
