package pl.edu.pjwstk.skmapi.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Compartment {
    private final int id;
    private final int compartmentPlacesNumber;
    private final List<Person> peopleOnBoard;

    public Compartment(int id, int compartmentPlacesNumber) {
        this.id = id;
        this.compartmentPlacesNumber = compartmentPlacesNumber;
        peopleOnBoard = new ArrayList<>();
    }

    public int getID() {
        return id;
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

    public void removePeopleFromCompartment(TrainStation currentTrainStation) {
        peopleOnBoard.removeIf(person -> person.getDestinationStation().getId() == currentTrainStation.getId());
    }

    public boolean isFull() {
        return peopleOnBoard.size() == compartmentPlacesNumber;
    }
}
