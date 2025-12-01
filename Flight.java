
import java.time.LocalDate;

/**
 * Entity representing a flight.
 */
public class Flight {

    private String flightNumber;
    private String origin;
    private String destination;
    private String flightDate;
    private String departureTime;
    private String arrivalTime;
    private int capacity;
    private int seatsReserved = 0;
    private float price;

    public Flight(String flightNumber, String flightDate,
                  String origin, String destination,
                  String departureTime, String arrivalTime,
                  int capacity, float price) {

        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.capacity = capacity;
        this.price = price;
    }

    public boolean isAvailable() {
        return seatsReserved < capacity;
    }

    public boolean reserveSeat() {
        if (!isAvailable()) {
            return false;
        }
        seatsReserved++;
        return true;
    }

    public boolean releaseSeat() {
        if (seatsReserved <= 0) {
            return false;
        }
        seatsReserved--;
        return true;
    }

    // Getters (used by other classes)
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getPrice() {
        return price;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public int getSeatsReserved() {
        return seatsReserved;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return flightNumber + " " + origin + "->" + destination + " at " + departureTime
                + " (capacity=" + capacity + ", reserved=" + seatsReserved + ")";
    }
}
