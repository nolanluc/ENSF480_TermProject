import javax.swing.*;
import java.awt.*;

public class CustomerDashboardGUI extends JFrame{
        public CustomerDashboardGUI() {
        super("Customer Menu");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        JButton searchFlightsBtn = new JButton("Search Flights");
        JButton viewReservationsBtn = new JButton("View Reservations");

        buttonPanel.add(searchFlightsBtn);
        buttonPanel.add(viewReservationsBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // FLIGHT SCREEN
        searchFlightsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Open Search Flights GUI");
            // new SearchFlightsGUI();
        });

        // MODIFY RESERVATION SCREEN
        viewReservationsBtn.addActionListener(e -> {
            dispose();
            new ModifyReservationScreen(null);
        });

        setVisible(true);
    }
}