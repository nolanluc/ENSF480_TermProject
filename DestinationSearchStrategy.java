import java.util.ArrayList;
import java.util.List;

/**
 * Concrete search strategy: by destination.
 */
public class DestinationSearchStrategy implements SearchStrategy {

    @Override
    public List<Flight> search(String criteria) {
        List<Flight> all = DatabaseManager.getInstance().getAllFlights();
        if (criteria == null || criteria.isBlank()) {
            return all;
        }
        List<Flight> result = new ArrayList<>();
        for (Flight f : all) {
            if (f.getDestination().equalsIgnoreCase(criteria)) {
                result.add(f);
            }
        }
        return result;
    }
}
