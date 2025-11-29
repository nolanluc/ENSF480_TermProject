import java.util.List;

/**
 * Strategy interface for searching flights.
 */
public interface SearchStrategy {

    List<Flight> search(String criteria);
}
