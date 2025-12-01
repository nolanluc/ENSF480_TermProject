import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FlightScreen extends JPanel {

    private SearchFlightController flightController;

    private List<Flight> flights;
    private Flight selectedFlight;

    private JList<String> flightList;
    private DefaultListModel<String> flightListModel;

    private JTextField dateField;
    private JTextField departureField;
    private JTextField landingField;
    private JTextField capacityField;

    public FlightScreen(SearchFlightController controller) {

        this.flightController = controller;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Flights"));

        // ===== Flight list =====
        flightListModel = new DefaultListModel<>();
        flightList = new JList<>(flightListModel);
        flightList.setBorder(
            BorderFactory.createTitledBorder("Scheduled Flights")
        );

        flightList.addListSelectionListener(e -> loadFlightDetails());

        add(new JScrollPane(flightList), BorderLayout.WEST);

        // ===== Details panel =====
        JPanel details = new JPanel(new GridLayout(4, 2, 10, 10));
        details.setBorder(
            BorderFactory.createTitledBorder("Flight Details")
        );

        dateField = new JTextField();
        departureField = new JTextField();
        landingField = new JTextField();
        capacityField = new JTextField();

        dateField.setEditable(false);
        departureField.setEditable(false);
        landingField.setEditable(false);
        capacityField.setEditable(false);

        details.add(new JLabel("Flight Date:"));
        details.add(dateField);

        details.add(new JLabel("Departure Time:"));
        details.add(departureField);

        details.add(new JLabel("Landing Time:"));
        details.add(landingField);

        details.add(new JLabel("Capacity:"));
        details.add(capacityField);

        add(details, BorderLayout.CENTER);

        loadFlights();
    }

    private void loadFlights() {

        flights = flightController.getAllFlights();
        flightListModel.clear();

        if (flights == null || flights.isEmpty()) {
            flightListModel.addElement("No flights found.");
            return;
        }

        // SORT BY DATE, THEN DEPARTURE TIME
        flights.sort((f1, f2) -> {
            int dateCompare = f1.getFlightDate()
                                .compareTo(f2.getFlightDate());
            if (dateCompare != 0) return dateCompare;

            return f1.getDepartureTime()
                    .compareTo(f2.getDepartureTime());
        });

        for (Flight f : flights) {
            flightListModel.addElement(
                f.getFlightNumber() + " | " +
                f.getOrigin() + " → " +
                f.getDestination() +
                " | " + f.getFlightDate() +
                " " + f.getDepartureTime()
            );
        }
    }

    private void loadFlightDetails() {

        int index = flightList.getSelectedIndex();
        if (index < 0 || flights == null || index >= flights.size()) return;

        selectedFlight = flights.get(index);

        dateField.setText(selectedFlight.getFlightDate());
        departureField.setText(selectedFlight.getDepartureTime());
        landingField.setText(selectedFlight.getArrivalTime());
        capacityField.setText(String.valueOf(selectedFlight.getCapacity()));
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }
}