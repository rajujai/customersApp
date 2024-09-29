package com.customers.app.controllers;

import com.customers.app.entities.Customer;
import com.customers.app.services.CustomerService;
import com.customers.app.utils.ApiResponse;
import com.customers.app.utils.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping
    public ApiResponse<?> getAllCustomers(Pagination pagination) {
        log.info("Getting all customers");
        return ApiResponse.pageResponse(service.getAllCustomers(pagination));
    }

    @GetMapping("/:id")
    public ApiResponse<Customer> getCustomerById(@PathVariable String id) {
        return ApiResponse.success(service.getCustomerById(id));
    }

    @PostMapping
    public ApiResponse<Customer> addCustomer(@RequestBody Customer customer) {
        return ApiResponse.success("Customer added successfully", service.addCustomer(customer));
    }

    @PutMapping
    public ApiResponse<Customer> updateCustomer(@RequestBody Customer customer) {
        return ApiResponse.success("Customer updated successfully with id: " + customer.getUuid(), service.updateCustomer(customer));
    }

    @DeleteMapping
    public ApiResponse<?> deleteCustomer(@RequestParam String id) {
        service.deleteCustomer(id);
        return ApiResponse.success("Customer deleted successfully");
    }
}
