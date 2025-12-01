import javax.swing.*;
import java.awt.*;

public class CustomerDashboardGUI extends JFrame {

    private Customer loggedInCustomer;

    public CustomerDashboardGUI(Customer customer) {

        super("Customer Menu");

        this.loggedInCustomer = customer;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton searchFlightsBtn = new JButton("Search Flights");
        JButton viewReservationsBtn = new JButton("View Reservations");
        JButton myReservationsBtn = new JButton("Manage My Reservations");

        buttonPanel.add(searchFlightsBtn);
        buttonPanel.add(viewReservationsBtn);
        buttonPanel.add(myReservationsBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // Search flights (placeholder)
        searchFlightsBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Open Search Flights GUI")
        );

        myReservationsBtn.addActionListener(e ->
            new ModifyReservationScreen(loggedInCustomer)
        );

        setVisible(true);
    }
}