/**
 * Factory for creating Reservation instances with incremental IDs.
 */
public class ReservationFactory {

    private static int nextId = 1;

    public Reservation createReservation(Customer customer, Flight flight) {
        // For now, 0 means "let DB assign" (once schema supports AUTOINCREMENT).
        return new Reservation(0, customer, flight);
    }
}
