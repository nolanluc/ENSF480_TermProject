import javax.swing.*;
import java.awt.*;

public class AddFlightGUI extends JFrame {

    private final AdminFlightManagementController controller;

    private JTextField flightNumberField;
    private JTextField flightDateField;
    private JTextField originField;
    private JTextField destinationField;
    private JTextField departureField;
    private JTextField arrivalField;
    private JTextField capacityField;
    private JTextField priceField;

    public AddFlightGUI(AdminFlightManagementController controller) {
        super("Add Flight");

        this.controller = controller;

        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        flightNumberField = new JTextField(12);
        flightDateField = new JTextField(12);
        originField = new JTextField(12);
        destinationField = new JTextField(12);
        departureField = new JTextField(12);
        arrivalField = new JTextField(12);
        capacityField = new JTextField(8);
        priceField = new JTextField(8);

        addRow("Flight #:", flightNumberField, 0, gbc);
        addRow("Flight Date (YYYY-MM-DD):", flightDateField, 1, gbc);
        addRow("Origin:", originField, 2, gbc);
        addRow("Destination:", destinationField, 3, gbc);
        addRow("Departure Time:", departureField, 4, gbc);
        addRow("Arrival Time:", arrivalField, 5, gbc);
        addRow("Capacity:", capacityField, 6, gbc);
        addRow("Price:", priceField, 7, gbc);

        JButton addBtn = new JButton("Add Flight");
        JButton cancelBtn = new JButton("Cancel");

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(addBtn, gbc);

        gbc.gridy = 9;
        add(cancelBtn, gbc);

        addBtn.addActionListener(e -> addFlight());
        cancelBtn.addActionListener(e -> {
            dispose();
            new AdminGUI();
        });

        setVisible(true);
    }

    // =========================
    // ADD LOGIC
    // =========================
    private void addFlight() {
        try {
            Flight flight = new Flight(
                flightNumberField.getText().trim(),
                flightDateField.getText().trim(),
                originField.getText().trim(),
                destinationField.getText().trim(),
                departureField.getText().trim(),
                arrivalField.getText().trim(),
                Integer.parseInt(capacityField.getText().trim()),
                Float.parseFloat(priceField.getText().trim()) 
            );

            if (!controller.validateInput(flight)) {
                showError("Invalid input. Please check all fields.");
                return;
            }

            boolean success = controller.saveFlight(flight);

            if (success) {
                JOptionPane.showMessageDialog(
                    this,
                    "Flight added successfully."
                );
                dispose();
                new AdminGUI();
            } else {
                showError("Flight number already exists.");
            }

        } catch (NumberFormatException e) {
            showError("Capacity and Price must be numeric.");
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(
                this,
                msg,
                "Input Error",
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