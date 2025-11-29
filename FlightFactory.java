/**
 * Factory for creating Flight instances.
 */
public class FlightFactory {

    public Flight createFlight(Flight data) {
        if (data == null) return null;
        return new Flight(
                data.getFlightNumber(),
                data.getOrigin(),
                data.getDestination(),
                data.getDepartureTime(),
                data.getArrivalTime(),
                data.getCapacity()
        );
    }
}
