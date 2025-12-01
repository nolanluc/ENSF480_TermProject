/**
 * Factory for creating Reservation instances with incremental IDs.
 */
public class ReservationFactory {

    private static int nextId = 1;

    public Reservation createReservation(Customer customer, Flight flight, String seatNumber) {
        int id = 0;
        return new Reservation(id, customer, flight, seatNumber);
    }
}
