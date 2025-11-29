/**
 * Entity representing a reservation.
 */
public class Reservation {

    private String reservationID;
    private Customer customer;
    private Flight flight;
    private String status;

    public Reservation(String reservationID, Customer customer, Flight flight, String status) {
        this.reservationID = reservationID;
        this.customer = customer;
        this.flight = flight;
        this.status = status;
    }

    public boolean updateReservation() {
        // For now, nothing to update automatically; returns true to indicate success.
        return true;
    }

    public boolean cancelReservation() {
        if ("CANCELLED".equals(status)) {
            return false;
        }
        status = "CANCELLED";
        flight.releaseSeat();
        return true;
    }

    public String getDetails() {
        return "Reservation " + reservationID + " [" + status + "] for customer "
                + (customer != null ? customer.getCustomerID() : "null")
                + " on flight " + (flight != null ? flight.getFlightNumber() : "null");
    }

    // Getters and setters used elsewhere
    public String getReservationID() {
        return reservationID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
