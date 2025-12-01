import java.awt.*;
import java.util.List;
import javax.swing.*;

public class AgentDashboardGUI extends JFrame {

    private List<Flight> flights;
    private List<Customer> customers;
    private JList<String> customerList;
    private Flight selectedFlight;
    private SearchFlightController flightController;
    private CustomerController customerController;
    private ReservationController reservationController;
    

    // Reservations UI
    private JList<String> reservationList;
    private JTextField seatField;
    private JButton saveReservationBtn;
    private JButton cancelReservationBtn;

    // Data
    private List<Reservation> reservations;



    //    CUSTOMER UI COMPONENTS
    private JTextArea customerInfoArea;

    //    CONSTRUCTOR
    public AgentDashboardGUI() {

        super("Agent Dashboard");

        flightController = new SearchFlightController(DatabaseManager.getInstance(), null);
        customerController = new CustomerController(DatabaseManager.getInstance());
        reservationController = new ReservationController(DatabaseManager.getInstance());

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Flight Schedule", createFlightPanel());
        tabs.addTab("Customers", createCustomerPanel());

        add(tabs);


        loadCustomers();

        setVisible(true);
    }


    //    FLIGHT TAB
    private JPanel createFlightPanel() {
        return new FlightScreen(flightController);
    }

    
    //    CUSTOMER TAB
       
    private JPanel createCustomerPanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // ===== TOP BAR =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton modifyCustomerBtn = new JButton("Modify Customer");
        JButton addCustomerBtn = new JButton("Add Customer");

        modifyCustomerBtn.addActionListener(e -> modifyCustomer());
        addCustomerBtn.addActionListener(e -> openAddCustomerScreen());

        topPanel.add(modifyCustomerBtn);
        topPanel.add(addCustomerBtn);
        panel.add(topPanel, BorderLayout.NORTH);

        // LEFT: Customers
        customerList = new JList<>();
        panel.add(new JScrollPane(customerList), BorderLayout.WEST);

        // CENTER: Reservations
        reservationList = new JList<>();
        reservationList.setBorder(
                BorderFactory.createTitledBorder("Reservations"));
        reservationList.addListSelectionListener(e -> loadReservationDetails());

        JPanel center = new JPanel(new BorderLayout());
        center.add(new JScrollPane(reservationList), BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);

        // RIGHT: Reservation Details
        JPanel right = new JPanel(new GridLayout(5, 1, 5, 5));
        right.setBorder(BorderFactory.createTitledBorder("Modify Reservation"));

        seatField = new JTextField();
        saveReservationBtn = new JButton("Save");
        cancelReservationBtn = new JButton("Cancel Reservation");

        right.add(new JLabel("Seat Number:"));
        right.add(seatField);
        right.add(saveReservationBtn);
        right.add(cancelReservationBtn);

        panel.add(right, BorderLayout.EAST);

        // Listeners
        customerList.addListSelectionListener(e -> loadCustomerReservations());
        reservationList.addListSelectionListener(e -> loadReservationDetails());

        saveReservationBtn.addActionListener(e -> saveReservation());
        cancelReservationBtn.addActionListener(e -> cancelReservation());

        return panel;
    }

    
    private void loadCustomers() {

        customers = customerController.getAllCustomers();

        if (customers == null || customers.isEmpty()) {
            customerList.setListData(new String[] { "No customers found." });
            return;
        }

        String[] listData = new String[customers.size()];
        for (int i = 0; i < customers.size(); i++) {
            listData[i] = customers.get(i).getCustomerID()
                    + " - " + customers.get(i).getName();
        }

        customerList.setListData(listData);
    }


    private void modifyCustomer() {

        int index = customerList.getSelectedIndex();

        if (index < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a customer.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Customer selectedCustomer = customers.get(index);

        ModifyCustomerScreen screen =
                new ModifyCustomerScreen(
                        this,
                        selectedCustomer,
                        customerController
                );

        screen.setVisible(true);

        loadCustomers();
    }
        public static void main(String[] args) {
        new AgentDashboardGUI();
    }

    private void loadCustomerReservations() {

        int index = customerList.getSelectedIndex();
        if (index < 0) return;

        Customer customer = customers.get(index);

        reservations =
            reservationController.getReservationsForCustomer(customer);

        if (reservations.isEmpty()) {
            reservationList.setListData(new String[] { "No reservations." });
            return;
        }

        String[] data = new String[reservations.size()];
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            data[i] =
                "Reservation #" + r.getReservationID() +
                " | " + r.getFlight().getFlightNumber() +
                " | " + r.getStatus();
        }

        reservationList.setListData(data);
    }

    private void loadReservationDetails() {

        int index = reservationList.getSelectedIndex();
        if (index < 0 || reservations == null) return;

        Reservation r = reservations.get(index);
        seatField.setText(r.getSeatNumber());
    }

    private void saveReservation() {

        int index = reservationList.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Select a reservation.");
            return;
        }

        Reservation r = reservations.get(index);
        r.setSeatNumber(seatField.getText());

        if (reservationController.updateReservation(r)) {
            JOptionPane.showMessageDialog(this, "Reservation updated.");
            loadCustomerReservations();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed.");
        }
    }

    private void cancelReservation() {

        int index = reservationList.getSelectedIndex();
        if (index < 0) return;

        Reservation r = reservations.get(index);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Cancel this reservation?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            reservationController.removeReservation(r.getReservationID());
            loadCustomerReservations();
        }
    }

    private void openAddCustomerScreen() {

        AddCustomerScreen screen =
            new AddCustomerScreen(this, customerController);

        screen.setVisible(true);

        if (screen.wasSaved()) {
            loadCustomers(); // refresh list
        }
    }

    private void openReservationManager() {
        new ModifyReservationScreen(); // AGENT MODE → loads all reservations
    }
}