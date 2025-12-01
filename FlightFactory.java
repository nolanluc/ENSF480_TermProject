public class FlightFactory {

    // Copy-constructor style
    public Flight createFlight(Flight data) {
        if (data == null) return null;
        return new Flight(
            data.getFlightNumber(),
            data.getFlightDate(),
            data.getOrigin(),
            data.getDestination(),
            data.getDepartureTime(),
            data.getArrivalTime(),
            data.getCapacity(),
            data.getPrice()
        );
    }

    // Overload if you ever want to create from raw fields
    public Flight createFlight(String flightNumber, String flightDate,
                               String origin, String destination,
                               String departureTime, String arrivalTime,
                               int capacity, float price) {
        return new Flight(flightNumber, flightDate, origin, destination,
                          departureTime, arrivalTime, capacity, price);
    }
}
