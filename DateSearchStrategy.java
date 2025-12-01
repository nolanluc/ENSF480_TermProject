import java.util.ArrayList;
import java.util.List;

/**
 * Concrete search strategy: by departure date (simple string contains check).
 * Expected criteria format example: "2025-05-12".
 */
public class DateSearchStrategy implements SearchStrategy {

    private final DatabaseManager db;

    public DateSearchStrategy(DatabaseManager db) {
        this.db = db;
    }

    @Override
    public List<Flight> search(String date) {
        return db.queryFlights(date);
    }
}