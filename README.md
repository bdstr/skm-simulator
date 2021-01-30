# SKM Simulator

REST API based on Spring Boot which simulates SKM railway network in Tri-City. There are entities of trains,
compartments and passengers. Everything is kept in MySQL database (initialized by Liquibase). It implements Spring
Security and uses JWT to authorize users.

Call to every endpoint apart from `/login` should include header Authorization with bearer token returned from `/login`
in form of `bearer token_from_login`.

### Available endpoints:

##### (can be imported to Postman from file `JAZ_skm-simulator.postman_collection.json` which automatically handles attaching token to requests)

- **API**:
    * `POST skmapi:9000/login` - authenticates user based on username and password provided in JSON and returns token
    * `POST skmapi:9000/move` - moves simulation step forward

    - Train:
        * `GET skmapi:9000/trains` - returns list of trains with details
        * `GET skmapi:9000/trains/{trainID}` - returns details of train with id = {trainID}
        * `POST skmapi:9000/trains` - adds a new train with details given in JSON to database
        * `DELETE skmapi:9000/trains/{trainID}` - deletes the train with id = {trainID} from database
        * `PUT skmapi:9000/trains` - updates train in the database based on details given in JSON
    - Compartment:
        * `GET skmapi:9000/compartments` - returns list of compartments with details
        * `GET skmapi:9000/compartments/{compartmentID}` - returns details of compartment with id = {compartmentID}
        * `POST skmapi:9000/compartments` - adds a new compartment with details given in JSON to database
        * `DELETE skmapi:9000/compartments/{compartmentID}` - deletes the compartment with id = {compartmentID} from
          database
        * `PUT skmapi:9000/compartments` - updates compartment in the database based on details given in JSON
    - User:
        * `GET skmapi:9000/users` - returns list of users with details
        * `GET skmapi:9000/users/{userID}` - returns details of user with id = {userID}
        * `POST skmapi:9000/users` - adds a new user with details given in JSON to database
        * `DELETE skmapi:9000/users/{userID}` - deletes the user with id = {userID} from database
        * `PUT skmapi:9000/users` - updates user in the database based on details given in JSON
- **CLIENT (currently not working due to lack of authentication support)**:
    * `GET skmclient:8080/show-trains` - returns data from skmapi:9000/trains
    * `GET skmclient:8080/show-train/{trainID}` - returns data from skmapi:9000/trains/{trainID}
    * `GET skmclient:8080/show-compartments` - returns data from skmapi:9000/compartments
    * `GET skmclient:8080/show-compartment/{compartmentID}` - returns data from skmapi:9000/compartments/{compartmentID}
