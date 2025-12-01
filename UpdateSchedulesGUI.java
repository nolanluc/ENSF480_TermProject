import javax.swing.*;
import java.awt.*;

public class UpdateSchedulesGUI extends JFrame {

    private JTextField flightIdField;
    private JTextField currentArrivalField, currentDepartureField;
    private JTextField newArrivalField, newDepartureField;

    public UpdateSchedulesGUI() {
        super("Update Flight Schedules");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        flightIdField = new JTextField(12);
        currentArrivalField = new JTextField(12);
        currentDepartureField = new JTextField(12);
        newArrivalField = new JTextField(12);
        newDepartureField = new JTextField(12);

        addField("Flight ID:", flightIdField, 0, gbc);
        addField("Current Arrival Time:", currentArrivalField, 1, gbc);
        addField("Current Departure Time:", currentDepartureField, 2, gbc);

        addField("New Arrival Time:", newArrivalField, 3, gbc);
        addField("New Departure Time:", newDepartureField, 4, gbc);

        JButton updateBtn = new JButton("Update Schedule");
        JButton backBtn = new JButton("Return to Admin Menu");

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(updateBtn, gbc);

        gbc.gridy = 6;
        add(backBtn, gbc);

        updateBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Schedule updated successfully!");
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
