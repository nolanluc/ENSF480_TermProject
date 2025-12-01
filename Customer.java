import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a customer.
 */
public class Customer {

    private int customerID;
    private String name;
    private String email;
    private String phone;
    private ArrayList<Reservation> reservations; //TEMP
    private String username;
    private String password;

    public Customer(int customerID, String name, String email, String phone) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.reservations = new ArrayList<>();
    }

    public Customer(int customerID, String name, String email, String phone, String username, String password) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.reservations = new ArrayList<>();
        this.username = username;
        this.password = password;
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
    public int getCustomerID() {
        return customerID;
    }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    // TEMP
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
}


