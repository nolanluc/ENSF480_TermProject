import javax.swing.*;
import java.awt.*;

public class BookingFlightGUI extends JFrame {

    private Customer customer;
    private Flight flight;
    private DatabaseManager db;
    private BookingController controller;

    private JTextField fullNameField;
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
        setSize(600, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buildUI();
        setVisible(true);
    }

    private void buildUI() {

        JPanel passengerPanel = new JPanel(new GridBagLayout());
        passengerPanel.setBorder(BorderFactory.createTitledBorder("Passenger Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        fullNameField = new JTextField(15);
        birthdayField = new JTextField(10);
        accessibilityBox = new JComboBox<>(new String[]{"No", "Yes"});

        addField(passengerPanel, gbc, "Full Name:", fullNameField, 0);
        addField(passengerPanel, gbc, "Birthday (YYYY-MM-DD):", birthdayField, 1);

        gbc.gridx = 0; gbc.gridy = 2;
        passengerPanel.add(new JLabel("Accessibility Request:"), gbc);

        gbc.gridx = 1;
        passengerPanel.add(accessibilityBox, gbc);

        add(passengerPanel, BorderLayout.NORTH);

        JPanel seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setBorder(BorderFactory.createTitledBorder("Seat Selection"));

        gbc.gridx = 0; gbc.gridy = 0;
        seatPanel.add(new JLabel("Select Seat:"), gbc);

        seatSelectBox = new JComboBox<>();
        loadAvailableSeats();

        gbc.gridx = 1;
        seatPanel.add(seatSelectBox, gbc);

        add(seatPanel, BorderLayout.CENTER);

        confirmButton = new JButton("Confirm Reservation");
        confirmButton.addActionListener(e -> handleReservation());

        JPanel bottom = new JPanel();
        bottom.add(confirmButton);

        add(bottom, BorderLayout.SOUTH);
    }

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

    private void handleReservation() {

        if (fullNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full name required.");
            return;
        }

        Reservation reservation = controller.confirmReservation(flight, customer);

        if (reservation == null) {
            JOptionPane.showMessageDialog(this, "Reservation failed.");
            return;
        }

        String selectedSeat = seatSelectBox.getSelectedItem().toString();
        reservation.setSeatNumber(selectedSeat);
        db.updateReservation(reservation);

        db.incrementSeats(flight.getFlightNumber());

        JOptionPane.showMessageDialog(this,
                "Reservation created!\nSeat: " + selectedSeat +
                "\nReservation: " + reservation.getReservationID());

        new PaymentGUI(customer, reservation);
        dispose();
    }

    private void addField(JPanel p, GridBagConstraints gbc, String text, JTextField field, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        p.add(new JLabel(text), gbc);

        gbc.gridx = 1;
        p.add(field, gbc);
    }
}
