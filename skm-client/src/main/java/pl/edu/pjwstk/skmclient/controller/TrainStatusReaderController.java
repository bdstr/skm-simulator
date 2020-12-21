package pl.edu.pjwstk.skmclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.edu.pjwstk.skmclient.model.CompartmentStatus;
import pl.edu.pjwstk.skmclient.model.TrainStatus;

import java.util.List;
import java.util.Map;

@RestController
public class TrainStatusReaderController {

    private final String trainsListURI = "http://skmapi:9000/trains/";
    private final String compartmentsListURI = "http://skmapi:9000/compartments/";

    @GetMapping("/show-trains")
    public List<?> getTrainsList() {
        RestTemplate template = new RestTemplate();
        return template.getForObject(trainsListURI, List.class);
    }

    @GetMapping("/show-train/{id}")
    public TrainStatus getTrainDetails(@PathVariable(value = "id") int id) {
        RestTemplate template = new RestTemplate();
        return template.getForObject(trainsListURI + id, TrainStatus.class);
    }

    @GetMapping("/show-compartments")
    public List<?> getCompartmentsList() {
        RestTemplate template = new RestTemplate();
        return template.getForObject(compartmentsListURI, List.class);
    }

    @GetMapping("/show-compartment/{compartmentID}")
    public CompartmentStatus getCompartmentDetails(@PathVariable(value = "compartmentID") int compartmentID) {
        RestTemplate template = new RestTemplate();
        return template.getForObject(compartmentsListURI + compartmentID, CompartmentStatus.class);
    }
}
