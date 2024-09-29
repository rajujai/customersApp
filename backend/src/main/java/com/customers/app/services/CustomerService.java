package com.customers.app.services;

import com.customers.app.entities.Customer;
import com.customers.app.repositories.CustomerRepository;
import com.customers.app.utils.Pagination;
import com.customers.app.utils.pagination.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public PageResponse<Customer> getAllCustomers(final Pagination pagination) {
        final Page<Customer> page = repository.findAll(pagination.pageRequest());
        return PageResponse.ofPage(page, pagination);
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
