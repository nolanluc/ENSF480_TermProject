import java.util.List;

public class DateSearchStrategy implements SearchStrategy {

    private final DatabaseManager db;

    public DateSearchStrategy(DatabaseManager db) {
        this.db = db;
    }

    @Override
    public List<Flight> search(String date) {

        if (date == null || date.isBlank()) {
            return List.of();
        }

        return db.getFlightsByDate(date.trim());
    }
}