import javax.swing.*;
import java.awt.*;

public class PaymentGUI extends JFrame {

    private Customer customer;
    private Reservation reservation;

    private DatabaseManager db;

    private JComboBox<String> paymentMethodBox;
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField expiryField;
    private JTextField cvvField;

    private JButton submitBtn;

    public PaymentGUI(Customer customer, Reservation reservation) {

        super("Payment");
        this.customer = customer;
        this.reservation = reservation;

        db = DatabaseManager.getInstance();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buildUI();
        setVisible(true);
    }

    private void buildUI() {

        JPanel top = new JPanel();
        paymentMethodBox = new JComboBox<>(new String[]{"Credit Card", "PayPal"});
        top.add(new JLabel("Payment Method:"));
        top.add(paymentMethodBox);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 2, 8, 8));

        cardNumberField = new JTextField();
        cardHolderField = new JTextField();
        expiryField = new JTextField();
        cvvField = new JTextField();

        center.add(new JLabel("Card Number:")); center.add(cardNumberField);
        center.add(new JLabel("Card Holder:")); center.add(cardHolderField);
        center.add(new JLabel("Expiry:")); center.add(expiryField);
        center.add(new JLabel("CVV:")); center.add(cvvField);

        add(center, BorderLayout.CENTER);

        submitBtn = new JButton("Submit Payment");
        submitBtn.addActionListener(e -> processPayment());

        JPanel bottom = new JPanel();
        bottom.add(submitBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private void processPayment() {

        BookingController controller = new BookingController(db);

        Payment payment = controller.sendPaymentDetails(
                reservation,
                reservation.getFlight().getPrice(),
                (String) paymentMethodBox.getSelectedItem()
        );

        if (payment == null || !"APPROVED".equals(payment.getStatus())) {
            JOptionPane.showMessageDialog(this, "Payment failed.");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Payment Complete!\nPayment ID: " + payment.getPaymentID());

        new CustomerDashboardGUI(customer);
        dispose();
    }
}
