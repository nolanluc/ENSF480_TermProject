import java.util.ArrayList;
import java.util.List;

/**
 * Concrete search strategy: by "price" using a very simple rule.
 * Since Flight currently has no price field in your UML,
 * this implementation just returns all flights (so the strategy exists for the pattern).
 */
public class PriceSearchStrategy implements SearchStrategy {

    private final DatabaseManager db;

    public PriceSearchStrategy(DatabaseManager db) {
        this.db = db;
    }

    @Override
    public List<Flight> search(String price) {
        return db.getAllFlights(); // until price field exists
    }
}