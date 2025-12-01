import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchFlightGUI extends JFrame {

    private SearchFlightController controller;
    private DatabaseManager db;
    private Customer loggedInCustomer;

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

    public SearchFlightGUI(Customer customer) {
        super("Search Flights");
        this.loggedInCustomer = customer;

        db = DatabaseManager.getInstance();
        controller = new SearchFlightController(db, null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buildUI();
        setVisible(true);
    }

    private void buildUI() {

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Flight Search"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        searchTypeBox = new JComboBox<>(new String[]{"Destination", "Departure Date", "Price"});

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Search By:"), gbc);
        gbc.gridx = 1;
        formPanel.add(searchTypeBox, gbc);

        cardLayout = new CardLayout();
        dynamicSearchPanel = new JPanel(cardLayout);

        JPanel destinationPanel = new JPanel(new FlowLayout());
        toCityField = new JTextField(12);
        destinationPanel.add(new JLabel("To City:"));
        destinationPanel.add(toCityField);

        JPanel datePanel = new JPanel(new FlowLayout());
        departureDateField = new JTextField(12);
        datePanel.add(new JLabel("Departure Date (YYYY-MM-DD):"));
        datePanel.add(departureDateField);

        JPanel pricePanel = new JPanel(new FlowLayout());
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
        formPanel.add(searchButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        resultList = new JList<>(new DefaultListModel<>());
        resultsPanel.add(new JScrollPane(resultList), BorderLayout.CENTER);

        bookButton = new JButton("Book Selected Flight");
        resultsPanel.add(bookButton, BorderLayout.SOUTH);

        add(resultsPanel, BorderLayout.CENTER);

        searchTypeBox.addActionListener(e ->
                cardLayout.show(dynamicSearchPanel, (String) searchTypeBox.getSelectedItem()));

        searchButton.addActionListener(e -> performSearch());
        bookButton.addActionListener(e -> openBooking());
    }

    private void performSearch() {
        String type = (String) searchTypeBox.getSelectedItem();

        switch (type) {
            case "Destination" -> controller.setStrategy(new DestinationSearchStrategy(db));
            case "Departure Date" -> controller.setStrategy(new DateSearchStrategy(db));
            case "Price" -> controller.setStrategy(new PriceSearchStrategy(db));
        }

        String criteria = switch (type) {
            case "Destination" -> toCityField.getText();
            case "Departure Date" -> departureDateField.getText();
            case "Price" -> minPriceField.getText() + "-" + maxPriceField.getText();
            default -> "";
        };

        List<Flight> results = controller.searchFlights(criteria);

        DefaultListModel<Flight> model = new DefaultListModel<>();
        for (Flight f : results) model.addElement(f);
        resultList.setModel(model);
    }

    private void openBooking() {

        Flight selected = resultList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a flight first!");
            return;
        }

        new BookingFlightGUI(loggedInCustomer, selected);
    }
}
