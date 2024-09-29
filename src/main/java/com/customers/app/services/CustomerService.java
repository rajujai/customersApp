package com.customers.app.services;

import com.customers.app.entities.Customer;
import com.customers.app.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Page<Customer> getAllCustomers(final PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    public Customer addCustomer(final Customer customer) {
        return repository.save(customer);
    }

    public Customer updateCustomer(final Customer customer) {
        return repository.save(customer);
    }

    public Customer getCustomerById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteCustomer(final String id) {
        repository.deleteById(id);
    }
}
