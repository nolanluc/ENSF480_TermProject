import javax.swing.*;
import java.awt.*;

public class ManageCustomerDataGUI extends JFrame {

    private JTextField agentLoginField;
    private JTextField customerNameField;
    private JButton submitButton;

    public ManageCustomerDataGUI() {
        super("Manage Customer Data");

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        agentLoginField = new JTextField(15);
        customerNameField = new JTextField(15);
        submitButton = new JButton("Submit");

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Agent Login:"), gbc);

        gbc.gridx = 1;
        add(agentLoginField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Customer Name:"), gbc);

        gbc.gridx = 1;
        add(customerNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            // Placeholder for linking to your future page
            JOptionPane.showMessageDialog(this, 
                "Next page will be implemented later.");
        });

        setVisible(true);
    }
}
