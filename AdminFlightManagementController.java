import java.util.List;

/**
 * Control class for admin operations on flights.
 */
public class AdminFlightManagementController {

    private final DatabaseManager db;
    private Flight currentFlight;

    public AdminFlightManagementController(DatabaseManager db) {
        this.db = db;
    }

    public boolean validateInput(Flight flightData) {
        if (flightData == null) return false;
        if (flightData.getFlightNumber() == null || flightData.getFlightNumber().isBlank()) return false;
        if (flightData.getOrigin() == null || flightData.getOrigin().isBlank()) return false;
        if (flightData.getDestination() == null || flightData.getDestination().isBlank()) return false;
        if (flightData.getCapacity() <= 0) return false;
        return true;
    }

    public Flight createFlight(Flight flightData) {
        if (!validateInput(flightData)) {
            return null;
        }
        // In a more advanced design you could call FlightFactory here.
        currentFlight = new Flight(
                flightData.getFlightNumber(),
                flightData.getOrigin(),
                flightData.getDestination(),
                flightData.getDepartureTime(),
                flightData.getArrivalTime(),
                flightData.getCapacity()
        );
        return currentFlight;
    }

    public boolean saveFlight(Flight flightData) {
        if (!validateInput(flightData)) {
            return false;
        }
        return db.saveFlight(flightData);
    }

    public Flight findFlight(String flightNumber) {
        currentFlight = db.getFlight(flightNumber);
        return currentFlight;
    }

    public boolean deleteFlight(int flightNumber) {
        return db.deleteFlight(flightNumber);
    }

    public List<Flight> listAllFlights() {
        return db.getAllFlights();
    }
}
