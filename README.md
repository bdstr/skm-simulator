# SKM Simulator

### Available endpoints:

#### (can be imported to Postman from file `JAZ_skm-simulator.postman_collection.json`)

- **API**:
    * `POST skmapi:9000/move` - moves simulation step forward
    * `GET skmapi:9000/trains` - returns list of trains with details
    * `GET skmapi:9000/trains/{trainID}` - returns details of train with id = {trainID}
    * `POST skmapi:9000/trains` - adds a new train with details given in JSON to database
    * `DELETE skmapi:9000/trains/{trainID}` - deletes the train with id = {trainID} from database
    * `PUT skmapi:9000/trains` - updates train in the database based on details given in JSON
    * `GET skmapi:9000/compartments` - returns list of compartments with details
    * `GET skmapi:9000/compartments/{compartmentID}` - returns details of compartment with id = {compartmentID}
    * `POST skmapi:9000/compartments` - adds a new compartment with details given in JSON to database
    * `DELETE skmapi:9000/compartments/{compartmentID}` - deletes the compartment with id = {compartmentID} from
      database
    * `PUT skmapi:9000/compartments` - updates compartment in the database based on details given in JSON
- **CLIENT**:
    * `GET skmclient:8080/show-trains` - returns data from skmapi:9000/trains
    * `GET skmclient:8080/show-train/{trainID}` - returns data from skmapi:9000/trains/{trainID}
    * `GET skmclient:8080/show-compartments` - returns data from skmapi:9000/compartments
    * `GET skmclient:8080/show-compartment/{compartmentID}` - returns data from skmapi:9000/compartments/{compartmentID}
