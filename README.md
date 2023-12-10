# Weather-Analyzer
REST API service for tracking and analyzing weather
### About The Project 
Weather Analyzer is an application developed based on Spring Boot, designed to receive weather data from a third-party service and analyze it. The application makes requests to an external API, receives weather data and stores it in a database for subsequent analysis.

### Endpoints:
* /current: Provides up-to-date weather information by presenting the latest weather record from the database
* /average: Allows the client to obtain weather averages based on data stored in the database for a specified period. The client sends the request in JSON format in the request body indicating the period (from and to), and receives in response the average values ​​of temperature, wind, pressure and humidity for this period.
