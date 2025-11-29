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
        reservation.updateReservation();
        return db.saveReservation(reservation);
    }

    public boolean removeReservation(String resID) {
        return db.deleteReservation(resID);
    }
}
