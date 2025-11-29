import java.util.List;

/**
 * Entity representing a customer.
 */
public class Customer {

    private String customerID;
    private String name;
    private String email;
    private String phone;

    public Customer(String customerID, String name, String email, String phone) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public List<Flight> searchFlights(String criteria) {
        // Delegate to DatabaseManager directly for simplicity.
        return DatabaseManager.getInstance().queryFlights(criteria);
    }

    public List<Reservation> viewReservations() {
        return DatabaseManager.getInstance().getReservationsForCustomer(this);
    }

    public Reservation makeBooking(Flight flight) {
        BookingController controller = new BookingController(DatabaseManager.getInstance());
        return controller.confirmReservation(flight, this);
    }

    public boolean modifyReservation(String reservationID) {
        ReservationController controller = new ReservationController(DatabaseManager.getInstance());
        Reservation res = controller.fetchReservation(reservationID);
        if (res == null) return false;
        // Simple "modify": change status to "MODIFIED"
        res.setStatus("MODIFIED");
        return controller.updateReservation(res);
    }

    public boolean cancelReservation(String reservationID) {
        ReservationController controller = new ReservationController(DatabaseManager.getInstance());
        Reservation res = controller.fetchReservation(reservationID);
        if (res == null) return false;
        boolean ok = res.cancelReservation();
        if (ok) {
            controller.updateReservation(res);
        }
        return ok;
    }

    // Getters (no setters for now, but you can add as needed)
    public String getCustomerID() {
        return customerID;
    }
}
