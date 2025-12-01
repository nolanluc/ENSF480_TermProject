import java.util.List;

public class CustomerController {

    private DatabaseManager db;

    public CustomerController(DatabaseManager db) {
        this.db = db;
    }

    public List<Customer> getAllCustomers() {
        return db.getAllCustomers();
    }

    public boolean updateCustomer(Customer customer) {
        return db.updateCustomer(customer);
    }

    public boolean addCustomer(Customer c) {
        return db.saveCustomer(c);
    }

    public Customer authenticate(String username, String password) {
        return db.authenticateCustomer(username, password);
    }

}