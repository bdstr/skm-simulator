package pl.edu.pjwstk.skmclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.edu.pjwstk.skmclient.model.CompartmentStatus;
import pl.edu.pjwstk.skmclient.model.TrainStatus;

import java.util.LinkedHashMap;

@RestController
public class TrainStatusReaderController {

    private final String trainsListURI = "http://skmapi:9000/trains/";

    @GetMapping("/show-trains-list")
    public LinkedHashMap getTrainsList() {
        RestTemplate template = new RestTemplate();
        return template.getForObject(trainsListURI, LinkedHashMap.class);
    }

    @GetMapping("/show-train-info/{id}")
    public TrainStatus getTrainDetails(@PathVariable(value = "id") int id) {
        RestTemplate template = new RestTemplate();
        return template.getForObject(trainsListURI + id, TrainStatus.class);
    }

    @GetMapping("/show-train-info/{trainID}/{compartmentID}")
    public CompartmentStatus getCompartmentDetails(@PathVariable(value = "trainID") int trainID,
                                                   @PathVariable(value = "compartmentID") int compartmentID) {
        RestTemplate template = new RestTemplate();
        return template.getForObject(trainsListURI + trainID + "/" + compartmentID, CompartmentStatus.class);
    }
}
