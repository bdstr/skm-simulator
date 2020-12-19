package pl.edu.pjwstk.skmapi.model;

import com.github.javafaker.Faker;
import pl.edu.pjwstk.skmapi.service.DbEntity;
import pl.edu.pjwstk.skmapi.utils.Randomizer;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Table(name = "people")
public class Person implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "destination_station")
    @Enumerated(EnumType.STRING)
    private Station destinationStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compartment_id")
    private Compartment compartment;

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }
}
