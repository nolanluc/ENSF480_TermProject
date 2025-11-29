/**
 * Boundary class for modifying an existing reservation.
 */
public class ModifyReservationScreen {

    private Reservation reservation;

    public void displayReservation(Reservation r) {
        this.reservation = r;
        System.out.println("Displaying reservation for modification: " +
                (r != null ? r.getDetails() : "null"));
    }

    public void requestUpdate(Reservation r) {
        System.out.println("Requesting reservation update for: " + r.getReservationID());
    }

    public void requestCancellation(String reservationID) {
        System.out.println("Requesting cancellation for reservation: " + reservationID);
    }
}
