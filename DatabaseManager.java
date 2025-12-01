import java.sql.*;
import java.time.LocalDate;
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
        Connection conn = DriverManager.getConnection(DB_URL);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }

        return conn;
    }

    //Customer CRUD
    public boolean saveCustomer(Customer c) {
        String sql = "INSERT INTO Customer (name, email, phone, username, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getName());
            stmt.setString(2, c.getEmail());
            stmt.setString(3, c.getPhone());
            stmt.setString(4, c.getUsername());
            stmt.setString(5, c.getPassword());
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
                        rs.getInt("customerID"),
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
                        rs.getInt("customerID"),
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
            stmt.setInt(4, c.getCustomerID());
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

    public Flight getFlight(String flightNumber) {

        String sql =
            "SELECT * FROM Flight WHERE flightNumber = ?";

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Flight(
                    rs.getString("flightNumber"),
                    rs.getString("flightDate"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getString("departureTime"),
                    rs.getString("arrivalTime"),
                    rs.getInt("capacity"),
                    rs.getFloat("price")
                );
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
                    rs.getString("flightDate"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getString("departureTime"),
                    rs.getString("arrivalTime"),
                    rs.getInt("capacity"),
                    rs.getFloat("price")
                ));
            }

        } catch (SQLException e) {
            System.err.println("getAllFlights Error: " + e.getMessage());
        }
        return list;
    }

    public List<Flight> queryFlights(String destination) {

        List<Flight> results = new ArrayList<>();

        if (destination == null || destination.isBlank()) {
            return results;
        }

        String sql =
            "SELECT * FROM Flight " +
            "WHERE LOWER(destination) = LOWER(?)";

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, destination.trim());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(new Flight(
                    rs.getString("flightNumber"),
                    rs.getString("flightDate"),        
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getString("departureTime"),
                    rs.getString("arrivalTime"),
                    rs.getInt("capacity"),      
                    rs.getFloat("price")              
                ));
            }

        } catch (SQLException e) {
            System.err.println("queryFlightsByDestination Error: " + e.getMessage());
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
        String sql = "INSERT INTO Reservation (customerID, flightNumber, status, seatNumber, paymentID)"
                   + " VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getCustomer().getCustomerID());
            stmt.setString(2, r.getFlight().getFlightNumber());
            stmt.setString(3, r.getStatus());
            stmt.setString(4, r.getSeatNumber());
            stmt.setNull(5, java.sql.Types.INTEGER); // payment assigned later

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
                    rs.getInt("reservationID"),
                    customer,
                    flight,
                    rs.getString("seatNumber")
                );

                r.setStatus(rs.getString("status"));
                r.setPaymentID(rs.getString("paymentID")); // ✅ RESTORED

                return r;
            }

        } catch (SQLException e) {
            System.err.println("getReservation Error: " + e.getMessage());
        }

        return null;
    }

    public List<Reservation> getReservationsForCustomer(Customer customer) {

        List<Reservation> list = new ArrayList<>();

        String sql =
            "SELECT r.reservationID, r.status, r.seatNumber, r.paymentID, " +
            "c.customerID, c.name, c.email, c.phone, " +
            "f.flightNumber, f.flightDate, f.origin, f.destination, " +
            "f.departureTime, f.arrivalTime, f.capacity, f.seatsReserved, f.price " +
            "FROM Reservation r " +
            "JOIN Customer c ON r.customerID = c.customerID " +
            "JOIN Flight f ON r.flightNumber = f.flightNumber " +
            "WHERE r.customerID = ?";

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customer.getCustomerID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Customer c = new Customer(
                    rs.getInt("customerID"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone")
                );

                Flight f = new Flight(
                    rs.getString("flightNumber"),
                    rs.getString("flightDate"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getString("departureTime"),
                    rs.getString("arrivalTime"),
                    rs.getInt("capacity"),
                    rs.getFloat("price")
                );

                Reservation r = new Reservation(
                    rs.getInt("reservationID"),
                    c,
                    f,
                    rs.getString("status"),
                    rs.getString("seatNumber")
                );

                r.setPaymentID(rs.getString("paymentID")); // ✅ RESTORED
                list.add(r);
            }

        } catch (SQLException e) {
            System.err.println("getReservationsForCustomer Error: " + e.getMessage());
        }

        return list;
    }

    public boolean updateReservation(Reservation r) {

        String sql =
            "UPDATE Reservation SET status = ?, seatNumber = ?, paymentID = ? " +
            "WHERE reservationID = ?";

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getStatus());
            stmt.setString(2, r.getSeatNumber());

            if (r.getPaymentID() == null)
                stmt.setNull(3, java.sql.Types.VARCHAR); 
            else
                stmt.setString(3, r.getPaymentID());

            stmt.setInt(4, r.getReservationID());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("updateReservation Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteReservation(int reservationID) {
        String sql = "DELETE FROM Reservation WHERE reservationID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationID);
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

    public boolean validateCustomer(String username, String password) {

        String sql = """
            SELECT 1
            FROM Customer
            WHERE username = ?
              AND password = ?
        """;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true if found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateFlightAgent(String username, String password) {

        String sql = """
            SELECT agentID
            FROM FlightAgent
            WHERE username = ?
            AND password = ?
        """;

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            return stmt.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

        public boolean validateAdmin(String username, String password) {

        String sql = """
            SELECT adminID
            FROM SystemAdministrator
            WHERE username = ?
            AND password = ?
        """;

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            return stmt.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer authenticateCustomer(String username, String password) {

        String sql =
            "SELECT * FROM Customer WHERE username = ? AND password = ?";

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                    rs.getInt("customerID"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }

        } catch (SQLException e) {
            System.err.println("authenticateCustomer Error: " + e.getMessage());
        }

        return null; // login failed
    }

    public boolean updateFlight(Flight flight) {
        String sql =
            "UPDATE Flight SET origin=?, destination=?, departureTime=?, arrivalTime=?, capacity=? " +
            "WHERE flightNumber=?";

        try (Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, flight.getOrigin());
            ps.setString(2, flight.getDestination());
            ps.setString(3, flight.getDepartureTime());
            ps.setString(4, flight.getArrivalTime());
            ps.setInt(5, flight.getCapacity());
            ps.setString(6, flight.getFlightNumber());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean saveFlight(Flight flight) {

        String sql =
            "INSERT INTO Flight (flightNumber, flightDate, origin, destination, " +
            "departureTime, arrivalTime, capacity, price) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flight.getFlightNumber());
            stmt.setString(2, flight.getFlightDate());
            stmt.setString(3, flight.getOrigin());
            stmt.setString(4, flight.getDestination());
            stmt.setString(5, flight.getDepartureTime());
            stmt.setString(6, flight.getArrivalTime());
            stmt.setInt(7, flight.getCapacity());
            stmt.setDouble(8, flight.getPrice());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("saveFlight (INSERT) Error: " + e.getMessage());
            return false;
        }
    }

    public List<Flight> getFlightsByDate(String date) {

        List<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM Flight WHERE flightDate = ?";

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Flight(
                    rs.getString("flightNumber"),
                    rs.getString("flightDate"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getString("departureTime"),
                    rs.getString("arrivalTime"),
                    rs.getInt("capacity"),
                    rs.getFloat("price")
                ));
            }

        } catch (SQLException e) {
            System.err.println("getFlightsByDate Error: " + e.getMessage());
        }

        return list;
    }
    

    public List<Flight> getFlightsByPriceRange(float min, float max) {

        List<Flight> list = new ArrayList<>();
        String sql =
            "SELECT * FROM Flight WHERE price BETWEEN ? AND ?";

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, min);
            stmt.setFloat(2, max);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Flight(
                    rs.getString("flightNumber"),
                    rs.getString("flightDate"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getString("departureTime"),
                    rs.getString("arrivalTime"),
                    rs.getInt("capacity"),
                    rs.getFloat("price")
                ));
            }

        } catch (SQLException e) {
            System.err.println("getFlightsByPriceRange Error: " + e.getMessage());
        }

        return list;
    }

}
