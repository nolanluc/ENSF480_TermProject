import java.util.List;

/**
 * Control class for searching flights using different strategies.
 */
public class SearchFlightController {

    private SearchStrategy strategy;
    private final DatabaseManager db;

    public SearchFlightController(DatabaseManager db, SearchStrategy strategy) {
        this.db = db;
        this.strategy = strategy;
    }

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Flight> searchFlights(String criteria) {
        if (strategy == null) {
            // Fallback: query directly from DB
            return db.queryFlights(criteria);
        }
        return strategy.search(criteria);
    }
}
