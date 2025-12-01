import java.util.List;

public class DestinationSearchStrategy implements SearchStrategy {

    private final DatabaseManager db;

    public DestinationSearchStrategy(DatabaseManager db) {
        this.db = db;
    }

    @Override
    public List<Flight> search(String destination) {

        if (destination == null || destination.isBlank()) {
            return List.of();
        }

        return db.queryFlights(destination.trim());
    }
}