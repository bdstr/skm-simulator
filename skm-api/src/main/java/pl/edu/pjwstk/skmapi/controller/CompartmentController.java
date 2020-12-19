package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Person;
import pl.edu.pjwstk.skmapi.service.CompartmentService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/compartments")
public class CompartmentController extends CrudController<Compartment> {

    @Autowired
    public CompartmentController(CompartmentService compartmentService) {
        super(compartmentService);
    }

    @Override
    public Function<Compartment, Map<String, Object>> transformToDTO() {
        return compartment -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", compartment.getId());
            payload.put("train_id", compartment.getTrain().getId());
            payload.put("capacity", compartment.getCapacity());
            payload.put("people_on_board", compartment.getPeopleOnBoard().stream()
                    .map(Person::getName)
                    .collect(Collectors.toList()));
            return payload;
        };
    }
}
