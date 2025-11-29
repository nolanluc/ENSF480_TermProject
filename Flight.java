/**
 * Entity representing a flight.
 */
public class Flight {

    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int capacity;
    private int seatsReserved = 0;

    public Flight(String flightNumber, String origin, String destination,
                  String departureTime, String arrivalTime, int capacity) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.capacity = capacity;
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

    @Override
    public String toString() {
        return flightNumber + " " + origin + "->" + destination + " at " + departureTime
                + " (capacity=" + capacity + ", reserved=" + seatsReserved + ")";
    }
}
