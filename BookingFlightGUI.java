import java.awt.*;
import javax.swing.*;

public class BookingFlightGUI extends JFrame {

    private Customer customer;
    private Flight flight;
    private DatabaseManager db;
    private BookingController controller;

    private JTextField nameField;
    private JTextField birthdayField;
    private JComboBox<String> accessibilityBox;

    private JComboBox<String> seatSelectBox;
    private JButton confirmButton;

    public BookingFlightGUI(Customer customer, Flight flight) {

        super("Book Flight");

        this.customer = customer;
        this.flight = flight;

        db = DatabaseManager.getInstance();
        controller = new BookingController(db);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buildUI();
        setVisible(true);
    }

    private void buildUI() {

        // =========================
        // Passenger Info Panel
        // =========================
        JPanel passengerPanel = new JPanel(new GridBagLayout());
        passengerPanel.setBorder(BorderFactory.createTitledBorder("Passenger Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Auto-filled customer name (read-only)
        nameField = new JTextField(customer.getName(), 15);
        nameField.setEditable(false);

        birthdayField = new JTextField(10);
        accessibilityBox = new JComboBox<>(new String[]{"No", "Yes"});

        addField(passengerPanel, gbc, "Passenger Name:", nameField, 0);
        addField(passengerPanel, gbc, "Birthday (YYYY-MM-DD):", birthdayField, 1);

        gbc.gridx = 0; gbc.gridy = 2;
        passengerPanel.add(new JLabel("Accessibility Request:"), gbc);

        gbc.gridx = 1;
        passengerPanel.add(accessibilityBox, gbc);

        add(passengerPanel, BorderLayout.NORTH);

        // =========================
        // Seat Selection Panel
        // =========================
        JPanel seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setBorder(BorderFactory.createTitledBorder("Seat Selection"));

        gbc.gridx = 0; gbc.gridy = 0;
        seatPanel.add(new JLabel("Select Seat:"), gbc);

        seatSelectBox = new JComboBox<>();
        loadAvailableSeats();

        gbc.gridx = 1;
        seatPanel.add(seatSelectBox, gbc);

        add(seatPanel, BorderLayout.CENTER);

        // =========================
        // Confirm Button
        // =========================
        confirmButton = new JButton("Confirm Reservation");
        confirmButton.addActionListener(e -> handleReservation());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(confirmButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // =========================
    // Load Seats
    // =========================
    private void loadAvailableSeats() {

        int capacity = flight.getCapacity();
        int reserved = flight.getSeatsReserved();

        for (int seat = reserved + 1; seat <= capacity; seat++) {
            seatSelectBox.addItem("Seat " + seat);
        }

        if (seatSelectBox.getItemCount() == 0) {
            seatSelectBox.addItem("No seats available");
            seatSelectBox.setEnabled(false);
            confirmButton.setEnabled(false);
        }
    }

    // =========================
    // Reservation Logic
    // =========================
    private void handleReservation() {

        String selectedSeat = seatSelectBox.getSelectedItem().toString();
        int seatNumber = Integer.parseInt(selectedSeat.replace("Seat ", ""));

        Reservation reservation =
            controller.confirmReservation(flight, customer);

        if (reservation == null) {
            JOptionPane.showMessageDialog(this,
                "Reservation failed. Flight may be full.");
            return;
        }

        JOptionPane.showMessageDialog(this,
            "Reservation confirmed for " + customer.getName() +
            "\nSeat: " + seatNumber +
            "\nReservation ID: " + reservation.getReservationID());

        new PaymentGUI(customer, reservation);
        dispose();
    }
    // =========================
    // Helper Method
    // =========================
    private void addField(JPanel panel, GridBagConstraints gbc,
                          String label, JTextField field, int row) {

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }
}