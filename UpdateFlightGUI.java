import javax.swing.*;
import java.awt.*;

public class UpdateFlightGUI extends JFrame {

    private final AdminFlightManagementController controller;
    private final Flight flight;

    private JTextField originField;
    private JTextField destinationField;
    private JTextField departureField;
    private JTextField arrivalField;
    private JTextField capacityField;
    private JTextField priceField;

    public UpdateFlightGUI(AdminFlightManagementController controller,
                           Flight flight) {

        super("Update Flight");

        this.controller = controller;
        this.flight = flight;

        setSize(420, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField flightNumberField =
                new JTextField(flight.getFlightNumber(), 12);
        flightNumberField.setEditable(false);

        originField = new JTextField(flight.getOrigin(), 12);
        destinationField = new JTextField(flight.getDestination(), 12);
        departureField = new JTextField(flight.getDepartureTime(), 12);
        arrivalField = new JTextField(flight.getArrivalTime(), 12);
        capacityField =
                new JTextField(String.valueOf(flight.getCapacity()), 8);
        priceField =
                new JTextField(String.valueOf(flight.getPrice()), 8);

        addRow("Flight #:", flightNumberField, 0, gbc);
        addRow("Origin:", originField, 1, gbc);
        addRow("Destination:", destinationField, 2, gbc);
        addRow("Departure:", departureField, 3, gbc);
        addRow("Arrival:", arrivalField, 4, gbc);
        addRow("Capacity:", capacityField, 5, gbc);
        addRow("Price:", priceField, 6, gbc);

        JButton saveBtn = new JButton("Save Changes");
        JButton cancelBtn = new JButton("Cancel");

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveBtn, gbc);

        gbc.gridy = 8;
        add(cancelBtn, gbc);

        saveBtn.addActionListener(e -> saveChanges());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    // =========================
    // SAVE USING YOUR CONTROLLER
    // =========================
    private void saveChanges() {
        try {
            flight.setOrigin(originField.getText().trim());
            flight.setDestination(destinationField.getText().trim());
            flight.setDepartureTime(departureField.getText().trim());
            flight.setArrivalTime(arrivalField.getText().trim());
            flight.setCapacity(Integer.parseInt(capacityField.getText().trim()));
            flight.setPrice(Float.parseFloat(priceField.getText().trim()));

            if (!controller.validateInput(flight)) {
                showError("Invalid input data.");
                return;
            }

            boolean success = controller.updateFlight(flight);

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Flight updated successfully."
                );
                dispose();
                new AdminGUI();
            } else {
                showError("Unable to save flight.");
            }

        } catch (NumberFormatException e) {
            showError("Capacity and price must be numeric.");
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(
                this,
                msg,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void addRow(String label, JComponent field, int row,
                        GridBagConstraints gbc) {

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        add(field, gbc);
    }
}