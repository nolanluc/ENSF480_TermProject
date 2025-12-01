import java.util.ArrayList;
import java.util.List;

/**
 * Concrete search strategy: by destination.
 */
public class DestinationSearchStrategy implements SearchStrategy {

    private final DatabaseManager db;

    public DestinationSearchStrategy(DatabaseManager db) {
        this.db = db;
    }

    @Override
    public List<Flight> search(String destination) {
        return db.queryFlights(destination);
    }
}