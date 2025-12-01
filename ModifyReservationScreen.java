import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModifyReservationScreen extends JFrame {

    private ReservationController reservationController;

    private Customer customer;          // null = agent
    private List<Reservation> reservations;

    private JList<String> reservationList;
    private JTextField flightField;
    private JTextField seatField;
    private JTextField statusField;

    private JButton saveBtn;
    private JButton cancelBtn;

    // =========================
    // AGENT CONSTRUCTOR
    // =========================
    public ModifyReservationScreen() {
        this(null);
    }

    // =========================
    // CUSTOMER CONSTRUCTOR
    // =========================
    public ModifyReservationScreen(Customer customer) {

        super("Modify Reservations");

        this.customer = customer;
        this.reservationController =
            new ReservationController(DatabaseManager.getInstance());

        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createUI());

        loadReservations();

        setVisible(true);
    }

    // =========================
    // UI
    // =========================
    private JPanel createUI() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        reservationList = new JList<>();
        reservationList.setBorder(
            BorderFactory.createTitledBorder("Reservations"));

        panel.add(new JScrollPane(reservationList), BorderLayout.WEST);

        JPanel details = new JPanel(new GridLayout(7, 1, 5, 5));
        details.setBorder(
            BorderFactory.createTitledBorder("Reservation Details"));

        flightField = new JTextField();
        seatField = new JTextField();
        statusField = new JTextField();

        flightField.setEditable(false);
        statusField.setEditable(false);

        saveBtn = new JButton("Save Changes");
        cancelBtn = new JButton("Cancel Reservation");

        details.add(new JLabel("Flight:"));
        details.add(flightField);
        details.add(new JLabel("Seat Number:"));
        details.add(seatField);
        details.add(new JLabel("Status:"));
        details.add(statusField);
        details.add(saveBtn);
        details.add(cancelBtn);

        panel.add(details, BorderLayout.CENTER);

        reservationList.addListSelectionListener(e -> loadDetails());
        saveBtn.addActionListener(e -> saveChanges());
        cancelBtn.addActionListener(e -> cancelReservation());

        return panel;
    }

    // =========================
    // LOAD DATA
    // =========================
    private void loadReservations() {

        if (customer == null) {
            // AGENT
            reservations = reservationController.getReservationsForCustomer(customer);
        } else {
            // CUSTOMER
            reservations =
                reservationController.getReservationsForCustomer(customer);
        }

        if (reservations == null || reservations.isEmpty()) {
            reservationList.setListData(
                new String[] { "No reservations found." });
            return;
        }

        String[] data = new String[reservations.size()];
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);

            data[i] =
                "Res #" + r.getReservationID() +
                " | " + r.getFlight().getFlightNumber() +
                (customer == null
                    ? " | Cust " + r.getCustomer().getCustomerID()
                    : "");
        }

        reservationList.setListData(data);
    }

    private void loadDetails() {

        int index = reservationList.getSelectedIndex();
        if (index < 0 || index >= reservations.size()) return;

        Reservation r = reservations.get(index);

        flightField.setText(
            r.getFlight().getFlightNumber() + " (" +
            r.getFlight().getOrigin() + " → " +
            r.getFlight().getDestination() + ")");

        seatField.setText(r.getSeatNumber());
        statusField.setText(r.getStatus());

        boolean cancelled = "CANCELLED".equals(r.getStatus());
        saveBtn.setEnabled(!cancelled);
        cancelBtn.setEnabled(!cancelled);
    }

    // =========================
    // ACTIONS
    // =========================
    private void saveChanges() {

        int index = reservationList.getSelectedIndex();
        if (index < 0) return;

        Reservation r = reservations.get(index);
        r.setSeatNumber(seatField.getText());

        if (reservationController.updateReservation(r)) {
            JOptionPane.showMessageDialog(this, "Reservation updated.");
            loadReservations();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed.");
        }
    }

    private void cancelReservation() {

        int index = reservationList.getSelectedIndex();
        if (index < 0) return;

        Reservation r = reservations.get(index);

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Cancel this reservation?",
            "Confirm",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            reservationController.removeReservation(r.getReservationID());
            JOptionPane.showMessageDialog(this, "Reservation cancelled.");
            loadReservations();
            flightField.setText("");
            seatField.setText("");
            statusField.setText("");
        }
    }
}