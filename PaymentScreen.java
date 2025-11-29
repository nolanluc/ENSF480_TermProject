/**
 * Boundary class for payment UI.
 */
public class PaymentScreen {

    public void showPaymentForm() {
        System.out.println("Showing payment form (Swing UI will replace this).");
    }

    public void showConfirmation(String msg) {
        System.out.println("[PAYMENT CONFIRM] " + msg);
    }

    public void showErrorMessage(String msg) {
        System.err.println("[PAYMENT ERROR] " + msg);
    }
}
