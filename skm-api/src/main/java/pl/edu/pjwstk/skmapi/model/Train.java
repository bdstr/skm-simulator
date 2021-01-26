package pl.edu.pjwstk.skmapi.model;

import pl.edu.pjwstk.skmapi.model.enums.Station;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trains")
public class Train implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Compartment> compartments;

    @Column(name = "current_station")
    @Enumerated(EnumType.STRING)
    private Station currentStation;

    private int direction;

    @Column(name = "waited_time_on_last_station")
    private int waitedTimeOnLastStation;


    public Train() {
        compartments = new HashSet<>();
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

    public double getPercentageOfTrainFilling() {
        return (100.0 / getTotalNumberOfPlacesInTrain()) * getNumberOfPeopleOnBoard();
    }

    public int getNumberOfPeopleOnBoard() {
        return compartments.stream()
                .map(Compartment::getNumberOfPeopleOnBoard)
                .reduce(0, Integer::sum);
    }

    public int getTotalNumberOfPlacesInTrain() {
        return compartments.stream()
                .map(Compartment::getCapacity)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public int getNumberOfFreePlacesInTrain() {
        return getTotalNumberOfPlacesInTrain() - getNumberOfPeopleOnBoard();
    }
}

