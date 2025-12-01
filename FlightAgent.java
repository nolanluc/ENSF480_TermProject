import java.util.List;

/**
 * Entity representing a flight agent user.
 */
public class FlightAgent {

    private int agentID;
    private String name;
    private String username;
    private String password;

    public FlightAgent(int agentID, String name) {
        this.agentID = agentID;
        this.name = name;
    }

    public List<Reservation> accessReservations() {
        throw new UnsupportedOperationException(
            "Accessing reservations by agent is not implemented."
        );
    }
    

    public boolean modifyReservation(String reservationID) {
        ReservationController controller = new ReservationController(DatabaseManager.getInstance());
        Reservation res = controller.fetchReservation(reservationID);
        if (res == null) return false;
        res.setStatus("MODIFIED_BY_AGENT");
        return controller.updateReservation(res);
    }

    public List<Flight> viewSchedules() {
        return DatabaseManager.getInstance().getAllFlights();
    }
}
