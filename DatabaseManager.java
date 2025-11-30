import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static DatabaseManager instance;
    private static final String DB_URL = "jdbc:sqlite:flights.db";

    private DatabaseManager() {}

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

   
    // Helper: Get Connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    //Customer CRUD
    public boolean saveCustomer(Customer c) {
        String sql = "INSERT INTO Customer (customerID, name, email, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getCustomerID());
            stmt.setString(2, c.getName());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getPhone());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("saveCustomer Error: " + e.getMessage());
            return false;
        }
    }

    public Customer getCustomer(String id) {
        String sql = "SELECT * FROM Customer WHERE customerID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getString("customerID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }

        } catch (SQLException e) {
            System.err.println("getCustomer Error: " + e.getMessage());
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Customer(
                        rs.getString("customerID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }

        } catch (SQLException e) {
            System.err.println("getAllCustomers Error: " + e.getMessage());
        }
        return list;
    }

    public boolean updateCustomer(Customer c) {
        String sql = "UPDATE Customer SET name = ?, email = ?, phone = ? WHERE customerID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getName());
            stmt.setString(2, c.getEmail());
            stmt.setString(3, c.getPhone());
            stmt.setString(4, c.getCustomerID());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("updateCustomer Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCustomer(String id) {
        String sql = "DELETE FROM Customer WHERE customerID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("deleteCustomer Error: " + e.getMessage());
            return false;
        }
    }

    //Flight CRUD
    public boolean saveFlight(Flight f) {
        String sql = "INSERT INTO Flight (flightNumber, origin, destination, departureTime, arrivalTime, capacity, seatsReserved)"
                   + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getFlightNumber());
            stmt.setString(2, f.getOrigin());
            stmt.setString(3, f.getDestination());
            stmt.setString(4, f.getDepartureTime());
            stmt.setString(5, f.getArrivalTime());
            stmt.setInt(6, f.getCapacity());
            stmt.setInt(7, 0);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("saveFlight Error: " + e.getMessage());
            return false;
        }
    }

    public Flight getFlight(String flightNumber) {
        String sql = "SELECT * FROM Flight WHERE flightNumber = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Flight f = new Flight(
                        rs.getString("flightNumber"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getString("departureTime"),
                        rs.getString("arrivalTime"),
                        rs.getInt("capacity")
                );
                return f;
            }

        } catch (SQLException e) {
            System.err.println("getFlight Error: " + e.getMessage());
        }
        return null;
    }

    public List<Flight> getAllFlights() {
        List<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM Flight";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Flight(
                        rs.getString("flightNumber"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getString("departureTime"),
                        rs.getString("arrivalTime"),
                        rs.getInt("capacity")
                ));
            }

        } catch (SQLException e) {
            System.err.println("getAllFlights Error: " + e.getMessage());
        }
        return list;
    }

    public List<Flight> queryFlights(String criteria) {
    List<Flight> results = new ArrayList<>();

    // If no search criteria, return all flights
    if (criteria == null || criteria.isBlank()) {
        return getAllFlights();
    }

    String sql = 
        "SELECT * FROM Flight " +
        "WHERE LOWER(origin) = LOWER(?) " +
        "   OR LOWER(destination) = LOWER(?) " +
        "   OR LOWER(flightNumber) = LOWER(?)";

    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Bind the same criteria to each condition
        stmt.setString(1, criteria);
        stmt.setString(2, criteria);
        stmt.setString(3, criteria);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            results.add(new Flight(
                    rs.getString("flightNumber"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getString("departureTime"),
                    rs.getString("arrivalTime"),
                    rs.getInt("capacity")
            ));
        }

    } catch (SQLException e) {
        System.err.println("queryFlights Error: " + e.getMessage());
    }

    return results;
}


    public boolean deleteFlight(String flightNumber) {
        String sql = "DELETE FROM Flight WHERE flightNumber = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightNumber);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("deleteFlight Error: " + e.getMessage());
            return false;
        }
    }

    // Seat management used by Reservation logic
    public boolean incrementSeats(String flightNumber) {
        String sql = "UPDATE Flight SET seatsReserved = seatsReserved + 1 WHERE flightNumber = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightNumber);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("incrementSeats Error: " + e.getMessage());
            return false;
        }
    }

    public boolean decrementSeats(String flightNumber) {
        String sql = "UPDATE Flight SET seatsReserved = seatsReserved - 1 WHERE flightNumber = ? AND seatsReserved > 0";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightNumber);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("decrementSeats Error: " + e.getMessage());
            return false;
        }
    }

    //Reservation CRUD
    public boolean saveReservation(Reservation r) {
        String sql = "INSERT INTO Reservation (reservationID, customerID, flightNumber, status, seatNumber, paymentID)"
                   + " VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getReservationID());
            stmt.setString(2, r.getCustomer().getCustomerID());
            stmt.setString(3, r.getFlight().getFlightNumber());
            stmt.setString(4, r.getStatus());
            stmt.setString(5, r.getSeatNumber());
            stmt.setString(6, null); // payment assigned later

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("saveReservation Error: " + e.getMessage());
            return false;
        }
    }

    public Reservation getReservation(String reservationID) {
        String sql = "SELECT * FROM Reservation WHERE reservationID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservationID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = getCustomer(rs.getString("customerID"));
                Flight flight = getFlight(rs.getString("flightNumber"));

                Reservation r = new Reservation(
                        rs.getString("reservationID"),
                        customer,
                        flight,
                        rs.getString("seatNumber")
                );

                r.setStatus(rs.getString("status"));
                return r;
            }

        } catch (SQLException e) {
            System.err.println("getReservation Error: " + e.getMessage());
        }
        return null;
    }

    public List<Reservation> getReservationsForCustomer(Customer customer) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT reservationID FROM Reservation WHERE customerID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getCustomerID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(getReservation(rs.getString("reservationID")));
            }

        } catch (SQLException e) {
            System.err.println("getReservationsForCustomer Error: " + e.getMessage());
        }

        return list;
    }

    public boolean updateReservation(Reservation r) {
        String sql = "UPDATE Reservation SET status = ?, seatNumber = ?, paymentID = ? WHERE reservationID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getStatus());
            stmt.setString(2, r.getSeatNumber());
            stmt.setString(3, null); // attach payment later
            stmt.setString(4, r.getReservationID());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("updateReservation Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteReservation(String reservationID) {
        String sql = "DELETE FROM Reservation WHERE reservationID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservationID);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("deleteReservation Error: " + e.getMessage());
            return false;
        }
    }

    //Payment CRUD
    public boolean savePayment(Payment p) {
        String sql = "INSERT INTO Payment (paymentID, amount, method, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getPaymentID());
            stmt.setDouble(2, p.getAmount());
            stmt.setString(3, p.getMethod());
            stmt.setString(4, p.getStatus());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("savePayment Error: " + e.getMessage());
            return false;
        }
    }

    public Payment getPayment(String paymentID) {
        String sql = "SELECT * FROM Payment WHERE paymentID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Payment p = new Payment(
                        rs.getDouble("amount"),
                        rs.getString("method")
                );
                p.setStatus(rs.getString("status"));
                return p;
            }

        } catch (SQLException e) {
            System.err.println("getPayment Error: " + e.getMessage());
        }
        return null;
    }

    public boolean updatePayment(Payment p) {
        String sql = "UPDATE Payment SET amount = ?, method = ?, status = ? WHERE paymentID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, p.getAmount());
            stmt.setString(2, p.getMethod());
            stmt.setString(3, p.getStatus());
            stmt.setString(4, p.getPaymentID());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("updatePayment Error: " + e.getMessage());
            return false;
        }
    }
}
