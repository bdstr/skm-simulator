package pl.edu.pjwstk.skmapi.model;

import pl.edu.pjwstk.skmapi.service.DbEntity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "compartments")
public class Compartment implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id")
    private Train train;

    private int capacity;

    @Transient
    private Set<Person> peopleOnBoard;


    public Compartment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<Person> getPeopleOnBoard() {
        return peopleOnBoard;
    }

    public void setPeopleOnBoard(Set<Person> peopleOnBoard) {
        this.peopleOnBoard = peopleOnBoard;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public List<String> getPeopleOnBoardNames() {
        LinkedList<String> peoplesNamesList = new LinkedList<>();
        peopleOnBoard.forEach(person -> peoplesNamesList.add(person.getName()));
        return peoplesNamesList;
    }

    public int getNumberOfPeopleOnBoard() {
        return peopleOnBoard.size();
    }

    public void addPersonOnBoard(Person person) {
        peopleOnBoard.add(person);
    }

    public void removePeopleFromCompartment(Station currentStation) {
        peopleOnBoard.removeIf(person -> person.getDestinationStation().getId() == currentStation.getId());
    }

    public boolean isFull() {
        return peopleOnBoard.size() == capacity;
    }
}
