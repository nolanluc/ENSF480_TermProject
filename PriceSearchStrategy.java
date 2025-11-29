import java.util.ArrayList;
import java.util.List;

/**
 * Concrete search strategy: by "price" using a very simple rule.
 * Since Flight currently has no price field in your UML,
 * this implementation just returns all flights (so the strategy exists for the pattern).
 */
public class PriceSearchStrategy implements SearchStrategy {

    @Override
    public List<Flight> search(String criteria) {
        // You can extend Flight to have a price field and filter on it.
        return new ArrayList<>(DatabaseManager.getInstance().getAllFlights());
    }
}
