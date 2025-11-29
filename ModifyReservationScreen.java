import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModifyReservationScreen extends JFrame {

    private List<Reservation> reservations;
    private Reservation selectedReservation;

    private JList<String> reservationList;
    private DefaultListModel<String> listModel;

    private JTextArea reservationArea;
    private JTextArea flightArea;

    private JButton modifyBtn;
    private JButton cancelBtn;
    private JButton closeBtn;

    public ModifyReservationScreen(Customer customer) {

        super("Modify Reservations");

        this.reservations = customer.getReservations();

        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // =========================
        // TITLE
        // =========================
        JLabel title = new JLabel("My Reservations", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // =========================
        // LEFT: RESERVATION LIST
        // =========================
        listModel = new DefaultListModel<>();
        for (Reservation r : reservations) {
            listModel.addElement(
                r.getFlight().getOrigin() + " → " +
                r.getFlight().getDestination() +
                " | Seat " + r.getSeatNumber() +
                " | " + r.getDetails().split("\n")[0]
            );
        }

        reservationList = new JList<>(listModel);
        reservationList.setBorder(
                BorderFactory.createTitledBorder("Select Reservation"));

        reservationList.addListSelectionListener(e -> loadReservation());

        add(new JScrollPane(reservationList), BorderLayout.WEST);

        // =========================
        // RIGHT: DETAILS
        // =========================
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        reservationArea = new JTextArea();
        reservationArea.setEditable(false);
        reservationArea.setBorder(
                BorderFactory.createTitledBorder("Reservation Details"));

        flightArea = new JTextArea();
        flightArea.setEditable(false);
        flightArea.setBorder(
                BorderFactory.createTitledBorder("Flight Details"));

        infoPanel.add(reservationArea);
        infoPanel.add(flightArea);

        add(infoPanel, BorderLayout.CENTER);

        // =========================
        // BUTTONS
        // =========================
        JPanel btnPanel = new JPanel(new FlowLayout());

        modifyBtn = new JButton("Modify");
        cancelBtn = new JButton("Cancel");
        closeBtn = new JButton("Close");

        btnPanel.add(modifyBtn);
        btnPanel.add(cancelBtn);
        btnPanel.add(closeBtn);

        add(btnPanel, BorderLayout.SOUTH);

        modifyBtn.addActionListener(e -> requestUpdate());
        cancelBtn.addActionListener(e -> requestCancellation());
        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    // =========================
    // LOAD SELECTED RESERVATION
    // =========================
    private void loadReservation() {
        int index = reservationList.getSelectedIndex();

        if (index >= 0) {
            selectedReservation = reservations.get(index);
            reservationArea.setText(selectedReservation.getDetails());
            flightArea.setText(
                    getFlightDetails(selectedReservation.getFlight()));
        }
    }

    // =========================
    // MODIFY
    // =========================
    private void requestUpdate() {
        if (selectedReservation == null) return;

        String newSeat = JOptionPane.showInputDialog(
                this,
                "Enter new seat number:");

        JOptionPane.showMessageDialog(this, "Reservation updated.");
    }

    // =========================
    // CANCEL
    // =========================
    private void requestCancellation() {
        if (selectedReservation == null) return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Cancel this reservation?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (selectedReservation.cancelReservation()) {
                reservationArea.setText(selectedReservation.getDetails());
                JOptionPane.showMessageDialog(this,
                        "Reservation cancelled.");
            }
        }
    }

    // =========================
    // HELPER
    // =========================
    private String getFlightDetails(Flight f) {
        return "Flight #: " + f.getFlightNumber() + "\n"
                + "From: " + f.getOrigin() + "\n"
                + "To: " + f.getDestination() + "\n"
                + "Departure: " + f.getDepartureTime() + "\n"
                + "Arrival: " + f.getArrivalTime();
    }
}