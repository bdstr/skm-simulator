package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Train;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TrainSimulatorController {

    private final List<Train> trainList;

    private final int trainsNumber;

    public TrainSimulatorController(@Value("${configuration.trainsNumber}") int trainsNumber) {
        this.trainsNumber = trainsNumber;
        trainList = new ArrayList<>();
        for (int i = 0; i < trainsNumber; i++) {
            trainList.add(new Train(i));
        }
    }

    @GetMapping("/trains")
    public List<Train> getTrainList() {
        return trainList;
    }

    @GetMapping("/trains/{id}")
    public Train getTrainByID(@PathVariable(value = "id") int trainID) {
        return trainList.stream()
                .filter(train -> trainID == train.getId())
                .findAny()
                .orElse(null);
    }

    @PostMapping("/move")
    public String moveForward() {
        for (Train train : trainList) {
            train.moveForward();
        }
        return "OK";
    }
}
