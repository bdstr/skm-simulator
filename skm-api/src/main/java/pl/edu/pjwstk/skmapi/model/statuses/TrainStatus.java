package pl.edu.pjwstk.skmapi.model.statuses;

import pl.edu.pjwstk.skmapi.model.Station;

public class TrainStatus {

    private final Long id;
    private final Station currentStation;
    private final int peopleNumber;
    private final double occupiedPlacesPercentage;

    public TrainStatus(Long id, Station currentStation, int peopleNumber, double occupiedPlacesPercentage) {
        this.id = id;
        this.currentStation = currentStation;
        this.peopleNumber = peopleNumber;
        this.occupiedPlacesPercentage = occupiedPlacesPercentage;
    }

    public Long getId() {
        return id;
    }

    public Station getCurrentTrainStation() {
        return currentStation;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public double getOccupiedPlacesPercentage() {
        return occupiedPlacesPercentage;
    }
}
