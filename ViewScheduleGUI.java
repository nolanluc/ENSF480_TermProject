import javax.swing.*;
import java.awt.*;

public class ViewScheduleGUI extends JFrame {

    public ViewScheduleGUI() {
        super("Flight Schedules");

        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel placeholder = new JLabel(
                "Schedule page will be implemented later.",
                SwingConstants.CENTER);

        placeholder.setFont(new Font("Arial", Font.ITALIC, 18));

        add(placeholder);

        setVisible(true);
    }
}
