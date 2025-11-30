/**
 * Entity representing a payment record.
 */
public class Payment {

    private static int nextId = 1;

    private String paymentID;
    private double amount;
    private String method;
    private String status;

    public Payment(double amount, String method) {
        this.paymentID = "PAY-" + nextId++;
        this.amount = amount;
        this.method = method;
        this.status = "PENDING";
    }

    public boolean processPayment() {
        // Success if amount > 0 as per your spec.
        if (amount > 0) {
            status = "APPROVED";
            return true;
        } else {
            status = "DECLINED";
            return false;
        }
    }

    public boolean refund() {
        if (!"APPROVED".equals(status)) {
            return false;
        }
        status = "REFUNDED";
        return true;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public String getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
}
