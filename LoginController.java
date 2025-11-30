public class LoginController {

    public static boolean login(String username, String password, String role) {

        switch (role) {

            case "Customer":
                return DatabaseManager.getInstance().validateCustomer(username, password);

            case "Flight Agent":
                return DatabaseManager.getInstance().validateFlightAgent(username, password);

            case "System Administrator":
                // you can wire this later
                return false;

            default:
                return false;
        }
    }
}