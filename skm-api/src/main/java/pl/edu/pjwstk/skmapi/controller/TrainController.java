package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Train;
import pl.edu.pjwstk.skmapi.service.TrainService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/trains")
public class TrainController extends CrudController<Train> {

    @Autowired
    public TrainController(TrainService trainService) {
        super(trainService);
    }

    @Override
    public Function<Train, Map<String, Object>> transformToDTO() {
        return train -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", train.getId());
            payload.put("current_station", train.getCurrentStation());
            payload.put("direction", train.getDirection());
            payload.put("waited_time_on_last_station", train.getWaitedTimeOnLastStation());
            payload.put("people_on_board", train.getNumberOfPeopleOnBoard());
            payload.put("filling_percentage", train.getPercentageOfTrainFilling());
            payload.put("compartments", train.getCompartments().stream().map(Compartment::getId));
            return payload;
        };
    }
}
