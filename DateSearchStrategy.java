import java.util.ArrayList;
import java.util.List;

/**
 * Concrete search strategy: by departure date (simple string contains check).
 * Expected criteria format example: "2025-05-12".
 */
public class DateSearchStrategy implements SearchStrategy {

    @Override
    public List<Flight> search(String criteria) {
        List<Flight> all = DatabaseManager.getInstance().getAllFlights();
        if (criteria == null || criteria.isBlank()) {
            return all;
        }
        List<Flight> result = new ArrayList<>();
        for (Flight f : all) {
            if (f.getDepartureTime() != null && f.getDepartureTime().contains(criteria)) {
                result.add(f);
            }
        }
        return result;
    }
}
