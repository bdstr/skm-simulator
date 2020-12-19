package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Train;
import pl.edu.pjwstk.skmapi.service.CompartmentService;
import pl.edu.pjwstk.skmapi.service.TrainService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/trains")
    public ResponseEntity<Map<String, Object>> getTrainsIDList() {
        var trains = trainService.getAll();

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("Trains number", trains.size());
        payload.put("Trains IDs", trains.stream().map(Train::getId).collect(Collectors.toList()));

        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
}
