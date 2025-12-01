import javax.swing.*;
import java.awt.*;

public class AdminGUI extends JFrame {

    private JButton updateFlightsBtn;
    private JButton manageRoutesBtn;
    private JButton updateSchedulesBtn;

    public AdminGUI() {
        super("Admin Panel");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel header = new JLabel("Admin Menu", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        add(header);

        updateFlightsBtn = new JButton("Add / Remove Flights");
        manageRoutesBtn = new JButton("Manage Routes & Aircraft");
        updateSchedulesBtn = new JButton("Update Flight Schedules");

        add(updateFlightsBtn);
        add(manageRoutesBtn);
        add(updateSchedulesBtn);

        updateFlightsBtn.addActionListener(e -> {
            new UpdateFlightsGUI();
            this.dispose();
        });

        manageRoutesBtn.addActionListener(e -> {
            new ManageRoutesGUI();
            this.dispose();
        });

        updateSchedulesBtn.addActionListener(e -> {
            new UpdateSchedulesGUI();
            this.dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminGUI();
    }
}
