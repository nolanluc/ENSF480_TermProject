import java.util.List;

/**
 * Boundary class for the booking UI.
 */
public class BookingScreen {

    public void displayFlights(List<Flight> flights) {
        System.out.println("Available flights:");
        if (flights == null || flights.isEmpty()) {
            System.out.println("  (none)");
            return;
        }
        for (Flight f : flights) {
            System.out.println("  " + f);
        }
    }

    public void showReservationSummary(Reservation reservation) {
        System.out.println("Reservation summary: " + (reservation != null ? reservation.getDetails() : "null"));
    }

    public void showErrorMessage(String msg) {
        System.err.println("[BOOKING ERROR] " + msg);
    }
}
