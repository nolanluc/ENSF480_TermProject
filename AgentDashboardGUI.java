import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AgentDashboardGUI extends JFrame {

    /* =========================
       TEMP IN-MEMORY DATA
       ========================= */
    private ArrayList<Flight> flights = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

    private Flight selectedFlight;

    /* =========================
       FLIGHT UI COMPONENTS
       ========================= */
    private JList<String> flightList;
    private DefaultListModel<String> flightListModel;

    private JTextField departureField;
    private JTextField arrivalField;
    private JTextField capacityField;

    /* =========================
       CUSTOMER UI COMPONENTS
       ========================= */
    private JTextArea customerInfoArea;
    private JTextField searchCustomerField;

    /* =========================
       CONSTRUCTOR
       ========================= */
    public AgentDashboardGUI() {

        super("Agent Dashboard");

        initSampleData();

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Flight Schedule", createFlightPanel());
        tabs.addTab("Customers", createCustomerPanel());

        add(tabs);
        setVisible(true);
    }

    /* =========================
       INITIALIZE SAMPLE DATA
       ========================= */
    private void initSampleData() {

        flights.add(new Flight("AC101", "Calgary", "Vancouver", "08:00", "09:30", 150));
        flights.add(new Flight("WS202", "Vancouver", "Toronto", "11:00", "18:00", 180));
        flights.add(new Flight("AC303", "Toronto", "New York", "20:00", "22:30", 120));

        customers.add(new Customer("C001", "Jane Doe", "jane@email.com", "403-111-2222"));
        customers.add(new Customer("C002", "John Smith", "john@email.com", "403-333-4444"));
    }

    /* =========================
       FLIGHT TAB
       ========================= */
    private JPanel createFlightPanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        flightListModel = new DefaultListModel<>();
        for (Flight f : flights) {
            flightListModel.addElement(
                f.getFlightNumber() + " | " + f.getOrigin() + " → " + f.getDestination()
            );
        }

        flightList = new JList<>(flightListModel);
        flightList.setBorder(BorderFactory.createTitledBorder("Flights"));
        flightList.addListSelectionListener(e -> loadFlight());

        panel.add(new JScrollPane(flightList), BorderLayout.WEST);

        JPanel details = new JPanel(new GridLayout(4, 2, 10, 10));
        details.setBorder(BorderFactory.createTitledBorder("Flight Details"));

        departureField = new JTextField();
        arrivalField = new JTextField();
        capacityField = new JTextField();


        details.add(new JLabel("Departure Time:"));
        details.add(departureField);
        details.add(new JLabel("Arrival Time:"));
        details.add(arrivalField);
        details.add(new JLabel("Capacity:"));
        details.add(capacityField);

        panel.add(details, BorderLayout.CENTER);
        return panel;
    }

    private void loadFlight() {
        int index = flightList.getSelectedIndex();
        if (index >= 0) {
            selectedFlight = flights.get(index);
            departureField.setText(selectedFlight.getDepartureTime());
            arrivalField.setText(selectedFlight.getArrivalTime());
            capacityField.setText(String.valueOf(selectedFlight.getCapacity()));
        }
    }

    /* =========================
       CUSTOMER TAB
       ========================= */
    private JPanel createCustomerPanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout());

        searchCustomerField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton addBtn = new JButton("Add Customer");

        searchPanel.add(new JLabel("Customer ID / Email:"));
        searchPanel.add(searchCustomerField);
        searchPanel.add(searchBtn);
        searchPanel.add(addBtn);

        panel.add(searchPanel, BorderLayout.NORTH);

        customerInfoArea = new JTextArea();
        customerInfoArea.setEditable(false);
        customerInfoArea.setBorder(BorderFactory.createTitledBorder("Customer Info"));

        panel.add(new JScrollPane(customerInfoArea), BorderLayout.CENTER);

        searchBtn.addActionListener(e -> searchCustomer());
        addBtn.addActionListener(e -> addCustomer());

        return panel;
    }

    private void searchCustomer() {
        // String input = searchCustomerField.getText().trim();

        // for (Customer c : customers) {
        //     if (c.getCustomerID().equalsIgnoreCase(input)
        //             || c.getEmail().equalsIgnoreCase(input)) {

        //         customerInfoArea.setText(
        //                 "ID: " + c.getCustomerID() +
        //                 "\nName: " + c.getName() +
        //                 "\nEmail: " + c.getEmail() +
        //                 "\nPhone: " + c.getPhone()
        //         );
        //         return;
        //     }
        // }
        // customerInfoArea.setText("Customer not found.");
    }

    private void addCustomer() {

        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();

        Object[] fields = {
            "ID:", id,
            "Name:", name,
            "Email:", email,
            "Phone:", phone
        };

        int result = JOptionPane.showConfirmDialog(
                this, fields, "Add Customer", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Customer c = new Customer(
                    id.getText(),
                    name.getText(),
                    email.getText(),
                    phone.getText()
            );
            customers.add(c);
            JOptionPane.showMessageDialog(this, "Customer added.");
        }
    }
}