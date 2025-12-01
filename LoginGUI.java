import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton loginBtn;

    public LoginGUI() {
        super("Login");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JLabel titleLabel = new JLabel("Flight Reservation Application", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // FORM PANEL
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        roleBox = new JComboBox<>(new String[]{
                "Customer",
                "Flight Agent",
                "System Administrator"
        });

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Login As:"), gbc);
        gbc.gridx = 1;
        formPanel.add(roleBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        // LOGIN BUTTON
        loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> handleLogin());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loginBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = LoginController.login(username, password, role);

        if(!success) {
            JOptionPane.showMessageDialog(this,
                "Invalid username/password",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
            "Login successful!\nRole: " + role,
            "Success",
        JOptionPane.INFORMATION_MESSAGE);

        dispose();

        if (role.equals("Customer")) {
            CustomerController customerController = new CustomerController(DatabaseManager.getInstance());
            Customer customer =
                customerController.authenticate(username, password);

            if (customer == null) {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            new CustomerDashboardGUI(customer);
            dispose();
            return;
        }

        if (role.equals("Flight Agent")) 
            new AgentDashboardGUI();

        if (role.equals("System Administrator")) 
            new AdminGUI();
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
