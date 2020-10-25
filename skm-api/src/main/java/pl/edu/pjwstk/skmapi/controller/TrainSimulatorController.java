package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Train;
import pl.edu.pjwstk.skmapi.model.TrainStatus;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TrainSimulatorController {

    private final List<Train> trainList;

    public TrainSimulatorController(@Value("${configuration.trainsNumber}") int trainsNumber,
                                    @Value("${configuration.compartmentsNumber}") int compartmentsNumber,
                                    @Value("${configuration.compartmentPlacesNumber}") int compartmentPlacesNumber) {
        trainList = new ArrayList<>();
        for (int i = 0; i < trainsNumber; i++) {
            trainList.add(new Train(i, compartmentsNumber, compartmentPlacesNumber));
        }
    }

    @GetMapping("/trains")
    public LinkedHashMap<String, Object> getTrainsIDList() {
        LinkedHashMap<String, Object> trainsStatus = new LinkedHashMap<>();
        trainsStatus.put("Trains number", trainList.size());
        trainsStatus.put("Trains IDs", trainList.stream().map(Train::getID).collect(Collectors.toList()));
        return trainsStatus;
    }

    @GetMapping("/trains/{id}")
    public LinkedHashMap<String, Object> getTrainByID(@PathVariable(value = "id") int trainID) {
        Train selectedTrain = trainList.stream()
                .filter(train -> trainID == train.getID())
                .findAny()
                .orElse(null);
        return new TrainStatus(selectedTrain).getTrainStatus();
    }

    @PostMapping("/move")
    public String moveForward() {
        for (Train train : trainList) {
            train.moveTrainSimulationStepForward();
        }
        return "OK";
    }
}
