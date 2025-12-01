import java.util.List;

public class CustomerController {

    private DatabaseManager db;

    public CustomerController(DatabaseManager db) {
        this.db = db;
    }

    public List<Customer> getAllCustomers() {
        return db.getAllCustomers();
    }

    public Customer findCustomer(String input) {

        if (input == null || input.trim().isEmpty())
            return null;

        for (Customer c : db.getAllCustomers()) {

            if (c.getCustomerID().equalsIgnoreCase(input)
                    || c.getEmail().equalsIgnoreCase(input)) {
                return c;
            }
        }
        return null;
    }

    public boolean updateCustomer(Customer customer) {
        return db.updateCustomer(customer);
    }

    public boolean addCustomer(Customer c) {
        return db.saveCustomer(c);
    }

}