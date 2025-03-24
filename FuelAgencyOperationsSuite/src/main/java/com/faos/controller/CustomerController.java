package com.faos.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faos.exception.InvalidEntityException;
import com.faos.model.Customer;
import com.faos.service.CustomerService;
import com.faos.service.EmailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/customers") 
@Validated
public class CustomerController {

	@Autowired
    CustomerService customerService;
	 @Autowired
	    private EmailService emailService;
	// Get all customers
    @GetMapping("/viewCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    // Add customer
    
    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        emailService.sendRegistrationEmail(savedCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

 // Search Customer by ID
    @GetMapping("/viewCustomerById/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable @Min(1) Long id) throws InvalidEntityException {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            throw new InvalidEntityException("Customer not found with ID: " + id);
        }
    }
    

 // Update Customer
    @PostMapping("/updateCustomer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable @Min(1) Long id, @Valid @RequestBody Customer updatedCustomer) throws InvalidEntityException {
        Customer customer = customerService.updateCustomer(id, updatedCustomer);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            throw new InvalidEntityException("Customer not found with ID: " + id);
        }
    }

 // Get Active Customers
    @GetMapping("/viewActiveCustomers")
    public ResponseEntity<List<Customer>> getActiveCustomers() {
        List<Customer> customers = customerService.getActiveCustomers();
        
        return ResponseEntity.ok(customers);
    }

    // Get Inactive Customers
    @GetMapping("/viewInactiveCustomers")
    public ResponseEntity<List<Customer>> getInactiveCustomers() {
        List<Customer> customers = customerService.getInactiveCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{customerId}/deactivate")
    public ResponseEntity<String> deactivateCustomer(@PathVariable @Min(1) Long customerId) throws InvalidEntityException {
        String response = customerService.setCustomerInactive(customerId);
     // Fetch the customer details after deactivation
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if (customer.isPresent()) {
            emailService.sendDeactivationEmail(customer.get());
        } else {
            throw new InvalidEntityException("Customer not found with ID: " + customerId);
        }
        return ResponseEntity.ok(response);
    }


}
