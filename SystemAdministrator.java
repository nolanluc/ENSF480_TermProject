/**
 * Entity representing a system administrator user.
 */
public class SystemAdministrator {

    private String adminID;
    private String name;
    private String username;
    private String password;

    public SystemAdministrator(String adminID, String name) {
        this.adminID = adminID;
        this.name = name;
    }

    public void manageRoutes() {
        System.out.println("SystemAdministrator.manageRoutes() called.");
    }

    public void manageAircraft() {
        System.out.println("SystemAdministrator.manageAircraft() called.");
    }

    public boolean updateSchedules() {
        System.out.println("Updating schedules...");
        return true;
    }

    public boolean addFlight(Flight flight) {
        return DatabaseManager.getInstance().saveFlight(flight);
    }

    public boolean removeFlight(String flightNumber) {
        return DatabaseManager.getInstance().deleteFlight(flightNumber);
    }

    
}
