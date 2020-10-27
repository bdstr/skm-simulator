package pl.edu.pjwstk.skmapi.model;

public class TrainStatus {

    private final int id;
    private final TrainStation currentTrainStation;
    private final int peopleNumber;
    private final double occupiedPlacesPercentage;

    public TrainStatus(int id, TrainStation currentTrainStation, int peopleNumber, double occupiedPlacesPercentage) {
        this.id = id;
        this.currentTrainStation = currentTrainStation;
        this.peopleNumber = peopleNumber;
        this.occupiedPlacesPercentage = occupiedPlacesPercentage;
    }

    public int getId() {
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
