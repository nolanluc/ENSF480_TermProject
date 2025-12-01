import javax.swing.*;
import java.awt.*;

public class ModifyCustomerScreen extends JDialog {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;

    private Customer customer;
    private CustomerController customerController;

    public ModifyCustomerScreen(
            JFrame parent,
            Customer customer,
            CustomerController controller) {

        super(parent, "Modify Customer", true);

        this.customer = customer;
        this.customerController = controller;

        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        //    FORM
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));

        nameField = new JTextField(customer.getName());
        emailField = new JTextField(customer.getEmail());
        phoneField = new JTextField(customer.getPhone());

        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Email:"));
        form.add(emailField);
        form.add(new JLabel("Phone:"));
        form.add(phoneField);

        add(form, BorderLayout.CENTER);

        //    BUTTONS
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        buttons.add(saveBtn);
        buttons.add(cancelBtn);

        add(buttons, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());
    }

    private void save() {

        customer.setName(nameField.getText().trim());
        customer.setEmail(emailField.getText().trim());
        customer.setPhone(phoneField.getText().trim());

        boolean success = customerController.updateCustomer(customer);

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Customer updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update customer.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}