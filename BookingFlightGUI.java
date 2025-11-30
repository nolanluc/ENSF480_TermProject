import javax.swing.*;
import java.awt.*;

public class BookingFlightGUI extends JFrame {

    // Passenger fields
    private JTextField fullNameField;
    private JTextField birthdayField;
    private JComboBox<String> accessibilityBox;

    // Placeholder for seat map
    private JPanel seatPanel;

    private JButton confirmButton;

    public BookingFlightGUI() {
        super("Book Flight");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===========================
        // TOP PANEL – PASSENGER INFO
        // ===========================
        JPanel passengerPanel = new JPanel(new GridBagLayout());
        passengerPanel.setBorder(BorderFactory.createTitledBorder("Passenger Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        // Fields
        fullNameField = new JTextField(15);
        birthdayField = new JTextField(10);
        accessibilityBox = new JComboBox<>(new String[]{"No", "Yes"});

        addField(passengerPanel, gbc, "Full Name:", fullNameField, 0);
        addField(passengerPanel, gbc, "Birthday (YYYY-MM-DD):", birthdayField, 1);

        // Accessibility row
        gbc.gridx = 0;
        gbc.gridy = 2;
        passengerPanel.add(new JLabel("Accessibility Request:"), gbc);

        gbc.gridx = 1;
        passengerPanel.add(accessibilityBox, gbc);

        add(passengerPanel, BorderLayout.NORTH);

        // ===========================
        // CENTER – SEAT AVAILABILITY PLACEHOLDER
        // ===========================
        seatPanel = new JPanel(new BorderLayout());
        seatPanel.setBorder(BorderFactory.createTitledBorder("Seat Availability"));

        JLabel placeholder = new JLabel("Seat selection will appear here.", SwingConstants.CENTER);
        placeholder.setFont(new Font("Arial", Font.ITALIC, 16));

        seatPanel.add(placeholder, BorderLayout.CENTER);

        // Large space for your future seat map
        add(seatPanel, BorderLayout.CENTER);

        // ===========================
        // BOTTOM – CONFIRM BUTTON
        // ===========================
        confirmButton = new JButton("Confirm Reservation");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(confirmButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Helper for adding rows
    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    public static void main(String[] args) {
        new BookingFlightGUI();
    }
}
