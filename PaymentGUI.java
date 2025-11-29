import javax.swing.*;
import java.awt.*;

public class PaymentGUI extends JFrame {

    // Components
    private JComboBox<String> paymentMethodBox;

    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField expiryField;
    private JTextField cvvField;

    private JTextField paypalEmailField;

    private JTextField billingNameField;
    private JTextField billingAddressField;
    private JTextField billingCityField;
    private JTextField billingPostalField;
    private JTextField billingCountryField;

    // Promo code components
    private JComboBox<String> promoChoiceBox;
    private JTextField promoCodeField;

    private JPanel creditCardPanel;
    private JPanel paypalPanel;

    private JButton submitBtn;

    public PaymentGUI() {
        super("Payment");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =======================
        // TOP - PAYMENT METHOD
        // =======================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Payment Method:"));

        paymentMethodBox = new JComboBox<>(new String[]{"Credit Card", "PayPal"});
        topPanel.add(paymentMethodBox);
        add(topPanel, BorderLayout.NORTH);

        // =======================
        // CENTER - PAYMENT PANELS
        // =======================
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Panel that switches: credit vs PayPal
        JPanel paymentTypePanel = new JPanel(new CardLayout());

        // ------ Credit Card Panel ------
        creditCardPanel = new JPanel(new GridBagLayout());
        creditCardPanel.setBorder(BorderFactory.createTitledBorder("Credit Card Details"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        cardNumberField = new JTextField(15);
        cardHolderField = new JTextField(15);
        expiryField = new JTextField(5);
        cvvField = new JTextField(4);

        addField(creditCardPanel, gbc, "Card Number:", cardNumberField, 0);
        addField(creditCardPanel, gbc, "Card Holder Name:", cardHolderField, 1);
        addField(creditCardPanel, gbc, "Expiry (MM/YY):", expiryField, 2);
        addField(creditCardPanel, gbc, "CVV:", cvvField, 3);

        // ------ PayPal Panel ------
        paypalPanel = new JPanel(new GridBagLayout());
        paypalPanel.setBorder(BorderFactory.createTitledBorder("PayPal Details"));

        paypalEmailField = new JTextField(20);
        addField(paypalPanel, gbc, "Email:", paypalEmailField, 0);

        paymentTypePanel.add(creditCardPanel, "Credit Card");
        paymentTypePanel.add(paypalPanel, "PayPal");

        // =======================
        // BILLING PANEL
        // =======================
        JPanel billingPanel = new JPanel(new GridBagLayout());
        billingPanel.setBorder(BorderFactory.createTitledBorder("Billing Information"));

        billingNameField = new JTextField(15);
        billingAddressField = new JTextField(15);
        billingCityField = new JTextField(10);
        billingPostalField = new JTextField(6);
        billingCountryField = new JTextField(10);

        addField(billingPanel, gbc, "Full Name:", billingNameField, 0);
        addField(billingPanel, gbc, "Address:", billingAddressField, 1);
        addField(billingPanel, gbc, "City:", billingCityField, 2);
        addField(billingPanel, gbc, "Postal Code:", billingPostalField, 3);
        addField(billingPanel, gbc, "Country:", billingCountryField, 4);

        // =======================
        // PROMOTION / COUPON PANEL
        // =======================
        JPanel promoPanel = new JPanel(new GridBagLayout());
        promoPanel.setBorder(BorderFactory.createTitledBorder("Promotion / Coupon"));

        promoChoiceBox = new JComboBox<>(new String[]{"No", "Yes"});
        promoCodeField = new JTextField(10);

        JLabel promoCodeLabel = new JLabel("Enter Code:");
        promoCodeLabel.setVisible(false);
        promoCodeField.setVisible(false);

        addField(promoPanel, gbc, "Do you have a promo code?", promoChoiceBox, 0);

        gbc.gridx = 0;
        gbc.gridy = 1;
        promoPanel.add(promoCodeLabel, gbc);

        gbc.gridx = 1;
        promoPanel.add(promoCodeField, gbc);

        promoChoiceBox.addActionListener(e -> {
            boolean wantsPromo = promoChoiceBox.getSelectedItem().equals("Yes");
            promoCodeLabel.setVisible(wantsPromo);
            promoCodeField.setVisible(wantsPromo);
            promoPanel.revalidate();
            promoPanel.repaint();
        });

        // Combine: payment type + billing + promo
        centerPanel.add(paymentTypePanel, BorderLayout.NORTH);
        centerPanel.add(billingPanel, BorderLayout.CENTER);
        centerPanel.add(promoPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Switch panel on payment method change
        paymentMethodBox.addActionListener(e -> {
            CardLayout cl = (CardLayout) paymentTypePanel.getLayout();
            cl.show(paymentTypePanel, paymentMethodBox.getSelectedItem().toString());
        });

        // =======================
        // SUBMIT BUTTON
        // =======================
        submitBtn = new JButton("Submit Payment");
        submitBtn.addActionListener(e -> handleSubmit());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submitBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleSubmit() {
        String method = (String) paymentMethodBox.getSelectedItem();

        if (billingNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Billing name required");
            return;
        }

        if (method.equals("Credit Card")) {
            if (cardNumberField.getText().isEmpty() ||
                cardHolderField.getText().isEmpty() ||
                expiryField.getText().isEmpty() ||
                cvvField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete all credit card fields.");
                return;
            }
        }

        if (method.equals("PayPal")) {
            if (paypalEmailField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "PayPal email required");
                return;
            }
        }

        if (promoChoiceBox.getSelectedItem().equals("Yes") &&
            promoCodeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your promo code.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Payment Submitted Successfully!");
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JComboBox<?> box, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(box, gbc);
    }

    public static void main(String[] args) {
        new PaymentGUI();
    }
}