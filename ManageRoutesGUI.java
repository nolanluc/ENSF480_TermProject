import javax.swing.*;
import java.awt.*;

public class ManageRoutesGUI extends JFrame {

    private JTextField flightIdField;
    private JTextField currentOriginField, currentDestField;
    private JTextField newOriginField, newDestField;

    public ManageRoutesGUI() {
        super("Manage Routes & Aircraft");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        flightIdField = new JTextField(12);
        currentOriginField = new JTextField(12);
        currentDestField = new JTextField(12);
        newOriginField = new JTextField(12);
        newDestField = new JTextField(12);

        addField("Flight ID:", flightIdField, 0, gbc);
        addField("Current Origin:", currentOriginField, 1, gbc);
        addField("Current Destination:", currentDestField, 2, gbc);

        addField("New Origin:", newOriginField, 3, gbc);
        addField("New Destination:", newDestField, 4, gbc);

        JButton updateBtn = new JButton("Update Route");
        JButton backBtn = new JButton("Return to Admin Menu");

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(updateBtn, gbc);

        gbc.gridy = 6;
        add(backBtn, gbc);

        updateBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Route successfully updated!");
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
