package pl.edu.pjwstk.skmapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Train;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TrainSimulatorController {

    private List<Train> trainList;

    public TrainSimulatorController() {
        int trainsNumber = 3;
        int compartmentsNumber = 16;
        int compartmentsPlacesNumber = 6;

        trainList = new ArrayList<>();
        for (int i = 0; i < trainsNumber; i++) {
            trainList.add(new Train(i, compartmentsNumber, compartmentsPlacesNumber));
        }
    }

    @GetMapping("/trains")
    public List<Train> getTrainList() {
        return trainList;
    }

    @PostMapping("/move")
    public String moveForward() {
        for (Train train : trainList) {
            train.moveForward();
        }
        return "OK";
    }
}
