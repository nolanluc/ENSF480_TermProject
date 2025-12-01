import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchFlightScreen extends JFrame {

    private final SearchFlightController controller;
    private final DatabaseManager db;
    private final Customer loggedInCustomer;

    // UI components
    private JComboBox<String> searchTypeBox;
    private JButton searchButton;
    private JButton bookButton;

    private JTextField toCityField;
    private JTextField departureDateField;
    private JTextField minPriceField;
    private JTextField maxPriceField;

    private JPanel dynamicSearchPanel;
    private CardLayout cardLayout;

    private JList<Flight> resultList;
    private JPanel resultsPanel;

    public SearchFlightScreen(Customer customer) {

        super("Search Flights");

        this.loggedInCustomer = customer;
        this.db = DatabaseManager.getInstance();
        this.controller = new SearchFlightController(db, null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buildUI();
        setVisible(true);
    }

    // =========================
    // UI
    // =========================
    private void buildUI() {

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Flight Search"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        searchTypeBox = new JComboBox<>(new String[]{
            "Destination",
            "Departure Date",
            "Price"
        });

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Search By:"), gbc);
        gbc.gridx = 1;
        formPanel.add(searchTypeBox, gbc);

        cardLayout = new CardLayout();
        dynamicSearchPanel = new JPanel(cardLayout);

        // Destination
        JPanel destinationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toCityField = new JTextField(12);
        destinationPanel.add(new JLabel("To City:"));
        destinationPanel.add(toCityField);

        // Date
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        departureDateField = new JTextField(12);
        datePanel.add(new JLabel("Departure Date (YYYY-MM-DD):"));
        datePanel.add(departureDateField);

        // Price
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

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        formPanel.add(dynamicSearchPanel, gbc);

        searchButton = new JButton("Search");
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(searchButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // =========================
        // Results
        // =========================
        resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        resultList = new JList<>(new DefaultListModel<>());
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        resultsPanel.add(new JScrollPane(resultList), BorderLayout.CENTER);

        bookButton = new JButton("Book Selected Flight");
        resultsPanel.add(bookButton, BorderLayout.SOUTH);

        add(resultsPanel, BorderLayout.CENTER);

        // =========================
        // Events
        // =========================
        searchTypeBox.addActionListener(e ->
            cardLayout.show(dynamicSearchPanel,
                            (String) searchTypeBox.getSelectedItem())
        );

        searchButton.addActionListener(e -> performSearch());
        bookButton.addActionListener(e -> openBooking());
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
            case "Price" -> minPriceField.getText().trim() + "-" +
                           maxPriceField.getText().trim();
            default -> "";
        };

        List<Flight> results = controller.searchFlights(criteria);

        DefaultListModel<Flight> model = new DefaultListModel<>();
        for (Flight f : results) {
            model.addElement(f);
        }

        resultList.setModel(model);
    }

    // =========================
    // Booking
    // =========================
    private void openBooking() {

        Flight selected = resultList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a flight first.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        new BookingFlightGUI(loggedInCustomer, selected);
    }
}