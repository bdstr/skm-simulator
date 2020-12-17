package pl.edu.pjwstk.skmapi.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Compartment {
    private final Long id;
    private final int capacity;
    private final List<Person> peopleOnBoard;

    public Compartment(Long id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        peopleOnBoard = new ArrayList<>();
    }

    public Long getID() {
        return id;
    }

    public List<String> getPeopleOnBoardNames() {
        LinkedList<String> peoplesNamesList = new LinkedList<>();
        peopleOnBoard.forEach(person -> peoplesNamesList.add(person.getName()));
        return peoplesNamesList;
    }

    public int getCapacity() {
        return capacity;
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
