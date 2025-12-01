import java.util.List;

public class PriceSearchStrategy implements SearchStrategy {

    private final DatabaseManager db;

    public PriceSearchStrategy(DatabaseManager db) {
        this.db = db;
    }

    @Override
    public List<Flight> search(String priceRange) {

        if (priceRange == null || priceRange.isBlank()) {
            return List.of();
        }

        try {
            String[] parts = priceRange.split("-");
            float min = Float.parseFloat(parts[0]);
            float max = Float.parseFloat(parts[1]);

            if (min > max) {
                float temp = min;
                min = max;
                max = temp;
            }

            return db.getFlightsByPriceRange(min, max);

        } catch (Exception e) {
            return List.of();
        }
    }
}