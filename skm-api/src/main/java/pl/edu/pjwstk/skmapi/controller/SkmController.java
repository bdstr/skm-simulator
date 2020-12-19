package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.service.SkmService;

@RestController
public class SkmController {

    private final SkmService skmService;

    @Autowired
    public SkmController(SkmService skmService) {
        this.skmService = skmService;
    }


    @PostMapping("/move")
    public ResponseEntity<HttpStatus> moveForward() {
        skmService.moveSimulationStepForward();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
