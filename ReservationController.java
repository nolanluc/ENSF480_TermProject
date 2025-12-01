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

    public boolean removeReservation(int resID) {

        // Fetch reservation first
        Reservation r = db.getReservation(String.valueOf(resID));
        if (r == null) return false;
    
        // Decrement seat count in DB
        db.decrementSeats(r.getFlight().getFlightNumber());
    
        // Delete reservation record
        return db.deleteReservation(resID);
    }
    

    public List<Reservation> getReservationsForCustomer(Customer customer) {
        return db.getReservationsForCustomer(customer);
    }
}
