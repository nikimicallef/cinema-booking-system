# cinema-booking-system

## Description
This is a basic cinema booking system. A user can book a seat for a film showing via the front end view. All the data is loaded at runtime via API calls to the server. The server loads initial data from a YAML properties. No data is persisted between different runs! 

Only film showings which are happening in the future are shown, so you might need to change the dated if no film showings appears. Please keep the yyyy-MM-dd HH:mm format.

## Technologies
Back end has been implemented using Java 8 and Spring (Core and MVC mainly). The front end view has been implemented using basic HTML and AngularJS. More focus has been made on having a complete and correct back end rather than having an impressive front end.

## Running the Application
Navigate to the root of the project (where there is the pom.xml file) and run

    mvn spring-boot:run

This will run all the tests and start the server. Once the server has been started navigate to 
    
    localhost:8080/index.html 

to view the input form.

## Assumptions
Assumptions have been kept to a minimum (hence the heavy usage of Optional) but an assumption has been made that the initial data is correct (i.e. max seat rows+columns can not be negative, film showing for a film actually exists etc.). The application should still work but unexpected behavior might occur. This assumption has been taken since the data input process is not done through a rigorous system with the relevant checks as an enterprise system, so this assumption has been taken.

## Improvements
The system is in no way complete. A number of TODOs have been left in the code as a placeholder for future work. Below are few of the possible improvements (excluding most of the TODOs listed in the code);

* Caching for when retrieving data from the entities. Periodically evict cache to retrieve updated.
* Cancellation of bookings
* Use of username + password for booking (possibly using Spring Security and/or Spring AOP)
* Cater for concurrent POST requests (when creating a booking) especially due to data consistency issues.
* Add functionality for front end to show more data related to the film (description, duration, film banner etc.) and introduce other entries such as Publisher.
* Add styling in a css file (and remove all styling within the HTML file)