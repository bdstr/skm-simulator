package pl.edu.pjwstk.skmapi.model;

import pl.edu.pjwstk.skmapi.service.DbEntity;
import pl.edu.pjwstk.skmapi.utils.Randomizer;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "trains")
public class Train implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Compartment> compartments;

    private Station currentStation;
    private int direction;
    private int waitedTimeOnLastStation;


    public Train() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWaitedTimeOnLastStation() {
        return waitedTimeOnLastStation;
    }

    public void setWaitedTimeOnLastStation(int waitedTimeOnLastStation) {
        this.waitedTimeOnLastStation = waitedTimeOnLastStation;
    }

    public Set<Compartment> getCompartments() {
        return compartments;
    }

    public void setCompartments(Set<Compartment> compartments) {
        this.compartments = compartments;
    }


    public void moveTrainSimulationStepForward() {
        if (currentStation.isLastStation()) {
            waitOnLastStation(currentStation.getPauseTime());
        } else {
            makeMoveToNextStation();
        }
    }

    public double getPercentageOfTrainFilling() {
        return (100.0 / getTotalNumberOfPlacesInTrain()) * getNumberOfPeopleOnBoard();
    }

    public int getNumberOfPeopleOnBoard() {
        return compartments.stream()
                .map(Compartment::getNumberOfPeopleOnBoard)
                .reduce(0, Integer::sum);
    }

    private int getTotalNumberOfPlacesInTrain() {
        return compartments.stream()
                .map(Compartment::getCapacity)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private void waitOnLastStation(int pauseTime) {
        if (waitedTimeOnLastStation >= pauseTime) {
            waitedTimeOnLastStation = 0;
            changeDirection();
            makeMoveToNextStation();
        } else {
            waitedTimeOnLastStation++;
        }
    }

    private void changeDirection() {
        if (currentStation.getId() == 0) {
            direction = 1;
        } else {
            direction = -1;
        }
    }

    private void makeMoveToNextStation() {
        getNewPeopleOnBoard();
        currentStation = Station.values()[currentStation.getId() + direction];
        removePeopleFromTrain();
    }

    private void getNewPeopleOnBoard() {
        int newPeopleNumber = Math.min(Randomizer.getRandomNumberInRange(2, 8), getNumberOfFreePlacesInTrain());
        for (int i = 0; i < newPeopleNumber; i++) {
            Compartment notFullCompartment = getRandomNotFullCompartment();
            notFullCompartment.addPersonOnBoard(new Person(currentStation, direction));
        }
    }

    private Compartment getRandomNotFullCompartment() {
        Compartment[] notFullCompartments = compartments.stream()
                .filter(compartment -> !compartment.isFull())
                .toArray(Compartment[]::new);
        return (Compartment) Randomizer.getRandomElementFromArray(notFullCompartments);
    }

    private int getNumberOfFreePlacesInTrain() {
        return getTotalNumberOfPlacesInTrain() - getNumberOfPeopleOnBoard();
    }

    private void removePeopleFromTrain() {
        compartments.forEach(compartment -> compartment.removePeopleFromCompartment(currentStation));
    }

}

