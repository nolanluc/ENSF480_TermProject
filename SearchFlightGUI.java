import javax.swing.*;
import java.awt.*;

public class SearchFlightGUI extends JFrame {

    // Search form components
    private JTextField fromCityField;
    private JTextField fromCountryField;
    private JTextField toCityField;
    private JTextField toCountryField;
    private JTextField departureDateField;
    private JTextField returnDateField;
    private JComboBox<Integer> passengerBox;

    private JButton searchButton;

    // Placeholder panel for flight results (empty now)
    private JPanel resultsPanel;

    public SearchFlightGUI() {
        super("Search Flights");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =========================
        // TOP PANEL – SEARCH INPUTS
        // =========================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Flight Search"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        // Create fields
        fromCityField = new JTextField(12);
        fromCountryField = new JTextField(12);
        toCityField = new JTextField(12);
        toCountryField = new JTextField(12);
        departureDateField = new JTextField(10);
        returnDateField = new JTextField(10);

        passengerBox = new JComboBox<>();
        for (int i = 1; i <= 10; i++) passengerBox.addItem(i);

        // Adding fields using helper
        addField(formPanel, gbc, "From City:", fromCityField, 0);
        addField(formPanel, gbc, "From Country:", fromCountryField, 1);
        addField(formPanel, gbc, "To City:", toCityField, 2);
        addField(formPanel, gbc, "To Country:", toCountryField, 3);
        addField(formPanel, gbc, "Departure Date (YYYY-MM-DD):", departureDateField, 4);
        addField(formPanel, gbc, "Return Date (optional):", returnDateField, 5);

        // Passengers row
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Passengers:"), gbc);

        gbc.gridx = 1;
        formPanel.add(passengerBox, gbc);

        // Search button
        searchButton = new JButton("Search");
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(searchButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // =========================
        // CENTER PANEL – RESULTS (EMPTY)
        // =========================
        resultsPanel = new JPanel();
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Flight Results"));
        resultsPanel.setLayout(new BorderLayout());

        // Placeholder (empty space for future flight results)
        JLabel placeholder = new JLabel("Flight results will appear here.", SwingConstants.CENTER);
        placeholder.setFont(new Font("Arial", Font.ITALIC, 16));
        resultsPanel.add(placeholder, BorderLayout.CENTER);

        add(resultsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Helper method for input rows
    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    public static void main(String[] args) {
        new SearchFlightGUI();
    }
}
