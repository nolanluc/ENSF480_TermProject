import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModifyReservationGUI extends JFrame {

    private JList<String> reservationList;
    private List<Reservation> reservations;
    private ReservationController controller;
    private JTextField agentLoginField;
    private JTextField customerNameField;
    private JTextField flightDetailsField;

    private JButton submitButton;

    public ModifyReservationGUI() {
        super("Modify Reservation");

        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        agentLoginField = new JTextField(15);
        customerNameField = new JTextField(15);
        flightDetailsField = new JTextField(20);

        submitButton = new JButton("Submit");

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Agent Login:"), gbc);
        gbc.gridx = 1;
        add(agentLoginField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 1;
        add(customerNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Flight Details:"), gbc);
        gbc.gridx = 1;
        add(flightDetailsField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Next page will be implemented later.");
        });

        setVisible(true);
    }

    public ModifyReservationGUI(
        JFrame parent,
        Customer customer,
        List<Reservation> reservations,
        ReservationController controller) {

            super("Reservations for " + customer.getName());
            this.reservations = reservations;
            this.controller = controller;

            setSize(500, 300);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout(10, 10));

            reservationList = new JList<>();
            add(new JScrollPane(reservationList), BorderLayout.CENTER);

            JButton modifyBtn = new JButton("Modify Reservation");
            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottom.add(modifyBtn);
            add(bottom, BorderLayout.SOUTH);

            loadReservations();

            modifyBtn.addActionListener(e -> modifyReservation());

            setVisible(true);
    }

        private void loadReservations() {

        String[] data = new String[reservations.size()];

        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            data[i] = "Reservation #" + r.getReservationID()
                    + " | " + r.getFlight().getFlightNumber()
                    + " | Seat " + r.getSeatNumber()
                    + " | " + r.getStatus();
        }

        reservationList.setListData(data);
    }

    private void modifyReservation() {

        int index = reservationList.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Select a reservation.");
            return;
        }

        Reservation selected = reservations.get(index);

        new ModifyReservationScreen(this, selected, controller);

        // Refresh list (in case status changed)
        loadReservations();
    }

}
