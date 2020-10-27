# SKM Simulator

### Available endpoints:
- API:
  * `POST skmapi:9000/move` - moves simulation step forward
  * `GET skmapi:9000/trains` - returns number of trains and trains IDs
  * `GET skmapi:9000/trains/{trainID}` - returns id, current train station, number of people and occupied places percentage of train with id = {trainID}
  * `GET skmapi:9000/trains/{trainID}/{compartmentID}` - returns id and names of people inside compartment with id = {compartmentID}
- CLIENT:
  * `GET skmclient:8080/show-trains-list` - returns data from skmapi:9000/trains
  * `GET skmclient:8080/show-train-info/{trainID}` - returns data from skmapi:9000/trains/{trainID}
  * `GET skmclient:8080/show-train-info/{trainID}/{compartmentID}` - returns data from skmapi:9000/trains/{trainID}/{compartmentID}
