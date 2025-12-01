import java.util.UUID;

public class Payment {

    private String paymentID;
    private double amount;
    private String method;
    private String status;

    public Payment(double amount, String method) {
        this.paymentID = UUID.randomUUID().toString(); 
        this.amount = amount;
        this.method = method;
        this.status = "PENDING";
    }

    public boolean processPayment() {
        if (amount >= 0) {
            status = "APPROVED";
            return true;
        } else {
            status = "DECLINED";
            return false;
        }
    }

    public boolean refund() {
        if (!"APPROVED".equals(status)) return false;
        status = "REFUNDED";
        return true;
    }

    public String getPaymentID() { return paymentID; }
    public String getStatus() { return status; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public void setStatus(String status) { this.status = status; }
}