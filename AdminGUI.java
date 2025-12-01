import javax.swing.*;
import java.awt.*;

public class AdminGUI extends JFrame {

    private final SearchFlightController flightController;
    private final FlightScreen flightScreen;
    private final AdminFlightManagementController adminController;

    private JButton addFlightBtn;
    private JButton updateFlightBtn;
    private JButton removeFlightBtn;
    private JButton backBtn;

    public AdminGUI() {

        super("System Administrator – Flight Management");

        flightController = new SearchFlightController(DatabaseManager.getInstance(), null);
        adminController = new AdminFlightManagementController(DatabaseManager.getInstance());

        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // CENTER – Shared Flight Screen
        flightScreen = new FlightScreen(flightController);
        add(flightScreen, BorderLayout.CENTER);

        // BOTTOM – Admin Controls
        JPanel adminControls = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        addFlightBtn = new JButton("Add Flight");
        updateFlightBtn = new JButton("Update Flight");
        removeFlightBtn = new JButton("Remove Flight (Quick)");
        backBtn = new JButton("Back");

        adminControls.add(addFlightBtn);
        adminControls.add(updateFlightBtn);
        adminControls.add(removeFlightBtn);
        adminControls.add(backBtn);

        add(adminControls, BorderLayout.SOUTH);

        // =========================
        // ACTIONS
        // =========================

        addFlightBtn.addActionListener(e -> {
        dispose();
        new AddFlightGUI(adminController);
        });

        updateFlightBtn.addActionListener(e -> {
                Flight selected = flightScreen.getSelectedFlight();

                if (selected == null) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Please select a flight first.",
                                "No Selection",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                }

                dispose();
                new UpdateFlightGUI(adminController, selected);
        });

        // 🔹 Quick remove using controller (without form)
        removeFlightBtn.addActionListener(e -> {
            Flight selected = flightScreen.getSelectedFlight();

            if (selected == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a flight first.",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Remove flight " + selected.getFlightNumber() + "?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success =
                        flightController.removeFlight(selected.getFlightNumber());

                if (success) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Flight removed successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();
                    new AdminGUI(); // reload
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Unable to remove flight.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminGUI::new);
    }
}