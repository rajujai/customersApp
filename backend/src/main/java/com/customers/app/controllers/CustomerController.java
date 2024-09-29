package com.customers.app.controllers;

import com.customers.app.entities.Customer;
import com.customers.app.exceptions.EntityNotFoundException;
import com.customers.app.exceptions.SyncCustomerException;
import com.customers.app.services.CustomerService;
import com.customers.app.services.external.SyncCustomerService;
import com.customers.app.utils.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    private final SyncCustomerService syncService;

    @GetMapping
    public ResponseEntity<?> getAllCustomers(Pagination pagination) {
        log.info("Getting all customers");
        return ResponseEntity.ok(service.getAllCustomers(pagination));
    }

    @GetMapping("/:id")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer add) {
        final Customer added = service.addCustomer(add);
        return ResponseEntity.accepted().body(added);
    }

    @PutMapping
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer update) {
        final Customer updated = service.updateCustomer(update);
        return ResponseEntity.accepted().body(updated);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCustomer(@RequestParam String id) {
        service.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted: " + id);
    }

    @GetMapping("/sync")
    public ResponseEntity<?> syncData() {
        try {
            syncService.syncCustomers();
            return ResponseEntity.ok("Data synchronized successfully");
        } catch (EntityNotFoundException | SyncCustomerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
