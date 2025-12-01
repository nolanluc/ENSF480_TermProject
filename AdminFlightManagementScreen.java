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
        // Not used in final Swing UI – kept only so the class compiles.
        return null;
    }

    public void showConfirmation(String message) {
        System.out.println("[ADMIN CONFIRMATION] " + message);
    }

    public String getCurrentOperation() {
        return currentOperation;
    }
}
