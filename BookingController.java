import java.util.List;

/**
 * Control class for the booking process (customer side).
 */
public class BookingController {

    private final DatabaseManager db;

    public BookingController(DatabaseManager db) {
        this.db = db;
    }

    public List<Flight> getFlightInfo(String criteria) {
        // Simple behaviour: delegate to DatabaseManager
        return db.queryFlights(criteria);
    }

    public Reservation confirmReservation(Flight flight, Customer customer) {
        if (flight == null || customer == null) return null;
        if (!flight.isAvailable()) {
            System.out.println("Flight is full, cannot confirm reservation.");
            return null;
        }
        Reservation reservation = new ReservationFactory().createReservation(customer, flight);
        if (db.saveReservation(reservation)) {
            flight.reserveSeat();
            return reservation;
        }
        return null;
    }

    public Payment sendPaymentDetails(Reservation reservation, double amount, String method) {
        if (reservation == null) return null;
        Payment payment = new Payment(amount, method);
        boolean ok = payment.processPayment();
        if (ok) {
            db.savePayment(payment);
            reservation.setStatus("CONFIRMED");
            db.saveReservation(reservation);
        } else {
            reservation.setStatus("PAYMENT_FAILED");
            db.saveReservation(reservation);
        }
        return payment;
    }
}
