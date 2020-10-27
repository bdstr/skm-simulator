package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.CompartmentStatus;
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

    @PostMapping("/move")
    public String moveForward() {
        for (Train train : trainList) {
            train.moveTrainSimulationStepForward();
        }
        return "OK";
    }

    @GetMapping("/trains")
    public LinkedHashMap<String, Object> getTrainsIDList() {
        LinkedHashMap<String, Object> trainsStatus = new LinkedHashMap<>();
        trainsStatus.put("Trains number", trainList.size());
        trainsStatus.put("Trains IDs", trainList.stream().map(Train::getID).collect(Collectors.toList()));
        return trainsStatus;
    }

    @GetMapping("/trains/{id}")
    public LinkedHashMap<String, Object> getTrainStatus(@PathVariable(value = "id") int trainID) {
        Train selectedTrain = getTrainByID(trainID);
        return new TrainStatus(selectedTrain).getStatus();
    }


    @GetMapping("/trains/{train_id}/{compartment_id}")
    public LinkedHashMap<String, Object> getCompartmentStatus(@PathVariable(value = "train_id") int trainID,
                                                              @PathVariable(value = "compartment_id") int compartmentID) {
        Compartment selectedCompartment = getCompartmentByID(trainID, compartmentID);
        return new CompartmentStatus(selectedCompartment).getStatus();
    }

    private Train getTrainByID(int trainID) {
        Train selectedTrain = null;
        try {
            selectedTrain = trainList.get(trainID);
        } catch (IndexOutOfBoundsException e) {
            throwResponseCode404("Train not found");
        }
        return selectedTrain;
    }

    private Compartment getCompartmentByID(int trainID, int compartmentID) {
        Train selectedTrain = getTrainByID(trainID);
        Compartment selectedCompartment = null;
        try {
            selectedCompartment = selectedTrain.getCompartmentsList().get(compartmentID);
        } catch (IndexOutOfBoundsException e) {
            throwResponseCode404("Train not found");
        }
        return selectedCompartment;
    }

    private void throwResponseCode404(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }
}
