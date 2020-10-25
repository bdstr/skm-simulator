package pl.edu.pjwstk.skmapi.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;

public class TrainStatus {

    private final Train train;

    public TrainStatus(Train train) {
        this.train = train;
    }

    public LinkedHashMap<String, Object> getTrainStatus() {
        checkIfTrainIsNotNull();
        LinkedHashMap<String, Object> status = new LinkedHashMap<>();
        status.put("ID", train.getID());
        status.put("Current station", train.getCurrentTrainStation().getName());
        status.put("Places occupied percentage", train.getPercentageOfTrainFilling());
        status.put("People number", train.getPeopleOnBoard().size());
        status.put("People", train.getPeopleOnBoard());
        return status;
    }

    private void checkIfTrainIsNotNull() {
        if (train == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Train not found!"
            );
        }
    }
}
