/**
 * Entity representing a reservation.
 */
public class Reservation {

    private int reservationID;
    private String paymentID;
    private Customer customer;
    private Flight flight;
    private String status;
    private String seatNumber;

    public Reservation(int reservationID, Customer customer, Flight flight, String seatNumber) {
        this.reservationID = reservationID;
        this.customer = customer;
        this.flight = flight;
        this.status = "CONFIRMED";
        this.seatNumber = seatNumber;
		this.paymentID = null;
    }

    public Reservation(int reservationID, Customer customer, Flight flight, String status, String seatNumber) {
        this.reservationID = reservationID;
        this.customer = customer;
        this.flight = flight;
        this.status = status;
        this.seatNumber = seatNumber;
		this.paymentID = null;
    }

    

    public Reservation(int reservationID, Customer customer, Flight flight) {
        this(reservationID, customer, flight, "NEW", null);
    }
    

    public boolean updateReservation() {
        // For now, nothing to update automatically; returns true to indicate success.
        return true;
    }

    public boolean cancelReservation() {
        if ("CANCELLED".equals(status)) return false;
    
        status = "CANCELLED";
    
        // Update both memory & DB
        flight.releaseSeat();
        DatabaseManager.getInstance().decrementSeats(flight.getFlightNumber());
    
        return true;
    }
    

    public String getDetails() {
        return "Reservation " + reservationID + " [" + status + "] for customer "
                + (customer != null ? customer.getCustomerID() : "null")
                + " on flight " + (flight != null ? flight.getFlightNumber() : "null");
    }

    // Getters and setters used elsewhere
    public int getReservationID() {
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
    
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
	
	public String getPaymentID() { 
		return paymentID; 
	
	}
	public void setPaymentID(String id) {
		this.paymentID = id; 
	}
}
