import java.util.List;

/**
 * Boundary class for the search flights UI.
 */
public class SearchFlightScreen {

    public void displayFlights(List<Flight> flights) {
        System.out.println("Search results:");
        if (flights == null || flights.isEmpty()) {
            System.out.println("  No flights found.");
            return;
        }
        for (Flight f : flights) {
            System.out.println("  " + f);
        }
    }

    public void showFlightDetails(Flight flight) {
        System.out.println("Flight details: " + flight);
    }

    public void showErrorMessage(String msg) {
        System.err.println("[SEARCH ERROR] " + msg);
    }
}
