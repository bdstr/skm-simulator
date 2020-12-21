package pl.edu.pjwstk.skmclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TrainStatus {

    private final Long id;
    private final TrainStation currentTrainStation;
    private final int direction;
    private final int waitedTimeOnLastStation;
    private final int peopleNumber;
    private final double occupiedPlacesPercentage;
    private final List<String> compartments;

    @JsonCreator
    public TrainStatus(@JsonProperty("id") Long id,
                       @JsonProperty("current_station") TrainStation currentTrainStation,
                       @JsonProperty("direction") int direction,
                       @JsonProperty("waited_time_on_last_station") int waitedTimeOnLastStation,
                       @JsonProperty("people_on_board") int peopleNumber,
                       @JsonProperty("filling_percentage") double occupiedPlacesPercentage,
                       @JsonProperty("compartments") List<String> compartments) {
        this.id = id;
        this.currentTrainStation = currentTrainStation;
        this.direction = direction;
        this.waitedTimeOnLastStation = waitedTimeOnLastStation;
        this.peopleNumber = peopleNumber;
        this.occupiedPlacesPercentage = occupiedPlacesPercentage;
        this.compartments = compartments;
    }

    public Long getId() {
        return id;
    }

    public int getDirection() {
        return direction;
    }

    public int getWaitedTimeOnLastStation() {
        return waitedTimeOnLastStation;
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

    public List<String> getCompartments() {
        return compartments;
    }
}
