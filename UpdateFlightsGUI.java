import javax.swing.*;
import java.awt.*;

public class UpdateFlightsGUI extends JFrame {

    private JTextField flightIdField, originField, destField;
    private JTextField departureTimeField, arrivalTimeField, capacityField;
    private JComboBox<String> actionBox;

    public UpdateFlightsGUI() {
        super("Add or Remove Flights");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        flightIdField = new JTextField(12);
        originField = new JTextField(12);
        destField = new JTextField(12);

        departureTimeField = new JTextField(10);
        arrivalTimeField = new JTextField(10);
        capacityField = new JTextField(8);

        actionBox = new JComboBox<>(new String[]{"Add Flight", "Remove Flight"});

        addField("Flight ID:", flightIdField, 0, gbc);
        addField("Origin:", originField, 1, gbc);
        addField("Destination:", destField, 2, gbc);
        addField("Departure Time:", departureTimeField, 3, gbc);
        addField("Arrival Time:", arrivalTimeField, 4, gbc);
        addField("Capacity:", capacityField, 5, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Action:"), gbc);
        gbc.gridx = 1;
        add(actionBox, gbc);

        JButton submitBtn = new JButton("Submit");
        JButton backBtn = new JButton("Return to Admin Menu");

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitBtn, gbc);

        gbc.gridy = 8;
        add(backBtn, gbc);

        submitBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Flight update submitted!");
        });

        backBtn.addActionListener(e -> {
            new AdminGUI();
            this.dispose();
        });

        setVisible(true);
    }

    private void addField(String label, JTextField field, int row, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        add(field, gbc);
    }
}
