package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.service.CompartmentService;
import pl.edu.pjwstk.skmapi.service.TrainService;

@RestController
public class SkmController {
    @Autowired
    TrainService trainService;

    @Autowired
    CompartmentService compartmentService;

    public SkmController() {
    }


    @PostMapping("/move")
    public ResponseEntity<HttpStatus> moveForward() {
        trainService.moveSimulationStepForward();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
