public class TestModifyReservations {

    public static void main(String[] args) {

        // =========================
        // CREATE CUSTOMER
        // =========================
        Customer customer = new Customer(
                "C001",
                "Jane Doe",
                "jane.doe@email.com",
                "403-555-1234"
        );

        // =========================
        // CREATE FLIGHTS
        // =========================
        Flight f1 = new Flight(
                "AC101",
                "Calgary",
                "Vancouver",
                "08:00",
                "09:30",
                150
        );

        Flight f2 = new Flight(
                "WS202",
                "Vancouver",
                "Toronto",
                "11:00",
                "18:00",
                180
        );

        Flight f3 = new Flight(
                "AC303",
                "Toronto",
                "New York",
                "20:00",
                "22:30",
                120
        );

        // =========================
        // CREATE RESERVATIONS
        // =========================
        Reservation r1 = new Reservation("R001", customer, f1, "14A");
        Reservation r2 = new Reservation("R002", customer, f2, "22C");
        Reservation r3 = new Reservation("R003", customer, f3, "3F");

        // =========================
        // ADD RESERVATIONS TO CUSTOMER (TEMP)
        // =========================
        customer.addReservation(r1);
        customer.addReservation(r2);
        customer.addReservation(r3);

        // =========================
        // OPEN GUI
        // =========================
        new ModifyReservationScreen(customer);
    }
}