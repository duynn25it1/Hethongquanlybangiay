package service;

import dao.CustomerDAO;
import model.Customer;

import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public void addCustomer(Customer customer) {
        customerDAO.insertCustomer(customer);
    }

    public List<Customer> getCustomers() {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {
        customerDAO.deleteCustomer(id);
    }
}
