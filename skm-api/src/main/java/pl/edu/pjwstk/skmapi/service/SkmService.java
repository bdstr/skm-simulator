package pl.edu.pjwstk.skmapi.service;

import org.springframework.stereotype.Service;

@Service
public class SkmService {
    private final TrainService trainService;

    public SkmService(TrainService trainService) {
        this.trainService = trainService;
    }

    public void moveSimulationStepForward() {
        trainService.moveSimulationStepForward();
    }
}
