import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Singleton responsible for data storage.
 * Currently uses in-memory collections, but you can replace internals with MySQL code.
 */
public class DatabaseManager {

    private static DatabaseManager instance;

    // In-memory "tables"
    private final List<Flight> flights = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();

    // Placeholder for future MySQL connection
    private Connection connection;

    private DatabaseManager() {
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    // ---- Flight operations ----

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }

    public List<Flight> queryFlights(String criteria) {
        if (criteria == null || criteria.isBlank()) {
            return getAllFlights();
        }
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDestination().equalsIgnoreCase(criteria)
                    || f.getOrigin().equalsIgnoreCase(criteria)
                    || f.getFlightNumber().equalsIgnoreCase(criteria)) {
                result.add(f);
            }
        }
        return result;
    }

    public Flight getFlight(String flightNumber) {
        for (Flight f : flights) {
            if (f.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                return f;
            }
        }
        return null;
    }

    public boolean saveFlight(Flight flight) {
        if (flight == null) return false;
        // update if exists
        Flight existing = getFlight(flight.getFlightNumber());
        if (existing != null) {
            flights.remove(existing);
        }
        flights.add(flight);
        return true;
    }

    /**
     * Delete a flight and all associated reservations.
     */
    public boolean deleteFlight(String flightNumber) {
        Flight target = getFlight(flightNumber);
        if (target == null) return false;

        // Remove reservations referencing this flight
        Iterator<Reservation> it = reservations.iterator();
        while (it.hasNext()) {
            Reservation r = it.next();
            if (r.getFlight() == target) {
                it.remove();
            }
        }

        flights.remove(target);
        return true;
    }

    // ---- Reservation operations ----

    public boolean saveReservation(Reservation reservation) {
        if (reservation == null) return false;
        Reservation existing = getReservation(reservation.getReservationID());
        if (existing != null) {
            reservations.remove(existing);
        }
        reservations.add(reservation);
        return true;
    }

    public boolean deleteReservation(String resID) {
        Reservation existing = getReservation(resID);
        if (existing == null) return false;
        existing.getFlight().releaseSeat();
        reservations.remove(existing);
        return true;
    }

    public Reservation getReservation(String resID) {
        for (Reservation r : reservations) {
            if (r.getReservationID().equalsIgnoreCase(resID)) {
                return r;
            }
        }
        return null;
    }

    public List<Reservation> getReservationsForCustomer(Customer customer) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getCustomer().equals(customer)) {
                result.add(r);
            }
        }
        return result;
    }

    // ---- Payment operations ----

    public boolean savePayment(Payment payment) {
        if (payment == null) return false;
        payments.add(payment);
        return true;
    }
}
