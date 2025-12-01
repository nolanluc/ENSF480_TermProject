import javax.swing.*;
import java.awt.*;

public class AddCustomerScreen extends JDialog {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private CustomerController controller;
    private boolean saved = false;

    public AddCustomerScreen(JFrame parent, CustomerController controller) {

        super(parent, "Add Customer", true);
        this.controller = controller;

        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Email:"));
        form.add(emailField);
        form.add(new JLabel("Phone:"));
        form.add(phoneField);
        form.add(new JLabel("Username:"));
        form.add(usernameField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        buttons.add(saveBtn);
        buttons.add(cancelBtn);

        add(buttons, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> saveCustomer());
        cancelBtn.addActionListener(e -> dispose());
    }

    private void saveCustomer() {
        
        if (nameField.getText().isBlank()
                || emailField.getText().isBlank()
                || usernameField.getText().isBlank()) {

            JOptionPane.showMessageDialog(this,
                    "Name, email and username are required.");
            return;
        }

        Customer c = new Customer(
                0, // auto-generated
                nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                usernameField.getText(),
                new String(passwordField.getPassword())
        );
        System.out.println("UI password = [" +
        new String(passwordField.getPassword()) + "]");
        if (controller.addCustomer(c)) {
            saved = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add customer.");
        }
    }

    public boolean wasSaved() {
        return saved;
    }
}