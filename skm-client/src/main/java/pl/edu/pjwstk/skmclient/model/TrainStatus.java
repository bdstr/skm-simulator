package pl.edu.pjwstk.skmclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TrainStatus {

    private final Long id;
    private final TrainStation currentTrainStation;
    private final int peopleNumber;
    private final double occupiedPlacesPercentage;

    @JsonCreator
    public TrainStatus(@JsonProperty("id") Long id,
                       @JsonProperty("currentTrainStation") TrainStation currentTrainStation,
                       @JsonProperty("peopleNumber") int peopleNumber,
                       @JsonProperty("occupiedPlacesPercentage") double occupiedPlacesPercentage) {
        this.id = id;
        this.currentTrainStation = currentTrainStation;
        this.peopleNumber = peopleNumber;
        this.occupiedPlacesPercentage = occupiedPlacesPercentage;
    }

    public Long getId() {
        return id;
    }

    public TrainStation getCurrentTrainStation() {
        return currentTrainStation;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public double getOccupiedPlacesPercentage() {
        return occupiedPlacesPercentage;
    }
}
