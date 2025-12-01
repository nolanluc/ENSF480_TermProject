import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchFlightGUI extends JFrame {

    private SearchFlightController controller;
    private DatabaseManager db;

    // Common fields
    private JComboBox<String> searchTypeBox;
    private JButton searchButton;

    // Destination search
    private JTextField toCityField;

    // Date search
    private JTextField departureDateField;

    // Price search
    private JTextField minPriceField;
    private JTextField maxPriceField;

    // Dynamic panel
    private JPanel dynamicSearchPanel;
    private CardLayout cardLayout;

    // Results panel
    private JPanel resultsPanel;

    public SearchFlightGUI() {

        super("Search Flights");

        db = DatabaseManager.getInstance();
        controller = new SearchFlightController(db, null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =========================
        // TOP PANEL
        // =========================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Flight Search"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search type dropdown
        searchTypeBox = new JComboBox<>(new String[]{
                "Destination",
                "Departure Date",
                "Price"
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Search By:"), gbc);

        gbc.gridx = 1;
        formPanel.add(searchTypeBox, gbc);

        // =========================
        // Dynamic Search Panel
        // =========================
        cardLayout = new CardLayout();
        dynamicSearchPanel = new JPanel(cardLayout);

        // Destination panel
        JPanel destinationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toCityField = new JTextField(12);
        destinationPanel.add(new JLabel("To City:"));
        destinationPanel.add(toCityField);

        // Date panel
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        departureDateField = new JTextField(12);
        datePanel.add(new JLabel("Departure Date (YYYY-MM-DD):"));
        datePanel.add(departureDateField);

        // Price panel
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        minPriceField = new JTextField(6);
        maxPriceField = new JTextField(6);
        pricePanel.add(new JLabel("Min Price:"));
        pricePanel.add(minPriceField);
        pricePanel.add(new JLabel("Max Price:"));
        pricePanel.add(maxPriceField);

        dynamicSearchPanel.add(destinationPanel, "Destination");
        dynamicSearchPanel.add(datePanel, "Departure Date");
        dynamicSearchPanel.add(pricePanel, "Price");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(dynamicSearchPanel, gbc);

        // Reset constraints
        gbc.gridwidth = 1;

        // =========================
        // Search button
        // =========================
        searchButton = new JButton("Search");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(searchButton, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        add(formPanel, BorderLayout.NORTH);

        // =========================
        // Results Panel
        // =========================
        resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Flight Results"));

        JLabel placeholder = new JLabel(
                "Flight results will appear here.",
                SwingConstants.CENTER
        );
        placeholder.setFont(new Font("Arial", Font.ITALIC, 16));

        resultsPanel.add(placeholder, BorderLayout.CENTER);
        add(resultsPanel, BorderLayout.CENTER);

        // =========================
        // Event listeners
        // =========================
        searchTypeBox.addActionListener(e -> {
            String selected = (String) searchTypeBox.getSelectedItem();
            cardLayout.show(dynamicSearchPanel, selected);
        });

        searchButton.addActionListener(e -> performSearch());

        setVisible(true);
    }

    // =========================
    // Search Logic
    // =========================
    private void performSearch() {

        String selected = (String) searchTypeBox.getSelectedItem();

        switch (selected) {
            case "Destination" ->
                    controller.setStrategy(new DestinationSearchStrategy(db));

            case "Departure Date" ->
                    controller.setStrategy(new DateSearchStrategy(db));

            case "Price" ->
                    controller.setStrategy(new PriceSearchStrategy(db));
        }

        String criteria = switch (selected) {
            case "Destination" -> toCityField.getText().trim();
            case "Departure Date" -> departureDateField.getText().trim();
            case "Price" ->
                    minPriceField.getText().trim() + "-" +
                    maxPriceField.getText().trim();
            default -> "";
        };

        List<Flight> results = controller.searchFlights(criteria);
        displayResults(results);
    }

    // =========================
    // Display Results
    // =========================
    private void displayResults(List<Flight> flights) {

        resultsPanel.removeAll();

        if (flights == null || flights.isEmpty()) {
            resultsPanel.add(
                    new JLabel("No matching flights found.", SwingConstants.CENTER),
                    BorderLayout.CENTER
            );
        } else {
            DefaultListModel<String> model = new DefaultListModel<>();
            JList<String> flightList = new JList<>(model);

            for (Flight f : flights) {
                model.addElement(
                        f.getFlightNumber() + " | " +
                        f.getOrigin() + " → " + f.getDestination() +
                        " | Departs: " + f.getDepartureTime() +
                        " | Capacity: " + f.getCapacity()
                );
            }

            resultsPanel.add(new JScrollPane(flightList), BorderLayout.CENTER);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchFlightGUI::new);
    }
}