package pl.edu.pjwstk.skmapi.model;

import java.util.LinkedHashMap;

public class TrainStatus implements Status {

    private final Train train;

    public TrainStatus(Train train) {
        this.train = train;
    }

    public LinkedHashMap<String, Object> getStatus() {
        LinkedHashMap<String, Object> status = new LinkedHashMap<>();
        status.put("ID", train.getID());
        status.put("Current station", train.getCurrentTrainStation().getName());
        status.put("Places occupied percentage", train.getPercentageOfTrainFilling());
        status.put("People number", train.getNumberOfPeopleOnBoard());
        return status;
    }
}
