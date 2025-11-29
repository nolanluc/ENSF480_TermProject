/**
 * Boundary class for the admin flight management UI.
 * Real Swing code can call these methods or replace the internals.
 */
public class AdminFlightManagementScreen {

    private String currentOperation;

    public void displayOptions() {
        System.out.println("Admin options: [ADD_FLIGHT, REMOVE_FLIGHT, UPDATE_FLIGHT]");
    }

    public void sendOperation(String operation) {
        this.currentOperation = operation;
        System.out.println("Selected operation: " + operation);
    }

    public Flight enterFlightData() {
        // For now just return a dummy flight; Swing UI will replace this.
        System.out.println("enterFlightData() called - provide real data from Swing UI");
        return new Flight("FL-0001", "YYC", "YVR", "2025-05-01 10:00", "2025-05-01 11:15", 100);
    }

    public void showConfirmation(String message) {
        System.out.println("[ADMIN CONFIRMATION] " + message);
    }

    public String getCurrentOperation() {
        return currentOperation;
    }
}
