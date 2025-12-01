import java.util.List;

/**
 * Control class for reservation retrieval and modification.
 */
public class ReservationController {

    private final DatabaseManager db;

    public ReservationController(DatabaseManager db) {
        this.db = db;
    }

    public Reservation fetchReservation(String resID) {
        return db.getReservation(resID);
    }

    public boolean updateReservation(Reservation reservation) {
        if (reservation == null) return false;
     
        return db.updateReservation(reservation);
    }

    public boolean removeReservation(Reservation r) {
        db.decrementSeats(r.getFlight().getFlightNumber());
        return db.deleteReservation(r.getReservationID());
    }

    public List<Reservation> getReservationsForCustomer(Customer customer) {
        return db.getReservationsForCustomer(customer);
    }
}
