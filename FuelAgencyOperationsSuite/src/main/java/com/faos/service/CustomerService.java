package com.faos.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.faos.exception.InvalidEntityException;
import com.faos.model.Customer;
import com.faos.model.Login;
import com.faos.model.enums.ConnectionStatus;
import com.faos.model.enums.UserRole;
import com.faos.repository.CustomerRepository;
import com.faos.repository.LoginRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

   
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Save a new customer
    @Transactional
    public Customer addCustomer(Customer customer) {
        // Save customer details first
        Customer savedCustomer = customerRepository.save(customer);
        
        // Generate a random 8-character password
        String rawPassword = UUID.randomUUID().toString().substring(0, 8);
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Create login entry
        Login login = new Login();
        login.setCustomer(savedCustomer);
        login.setRole(UserRole.CUSTOMER);
        login.setPassword(encodedPassword); // Save encoded password
        loginRepository.save(login);

        // Send login credentials via email
        emailService.sendRegistrationEmail(savedCustomer.getCustomerId(),savedCustomer.getEmail(), rawPassword);

        return savedCustomer;
    }

   

    // Fetch a customer by ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
        		
    }
    @Transactional
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isPresent()) {
            Customer existingCustomer = customerOptional.get();
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPhone(updatedCustomer.getPhone());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            existingCustomer.setConnectionStatus(updatedCustomer.getConnectionStatus());
            existingCustomer.setRegistrationDate(updatedCustomer.getRegistrationDate());
            existingCustomer.setConnectionType(updatedCustomer.getConnectionType());
            return customerRepository.save(existingCustomer); // Save updated customer
        } else {
            return null; // Customer not found
        }
    }
    public List<Customer> getActiveCustomers() {
    	
        return customerRepository.findByConnectionStatus(ConnectionStatus.ACTIVE);
    }

    public List<Customer> getInactiveCustomers() {
        return customerRepository.findByConnectionStatus(ConnectionStatus.INACTIVE);
    }
//deactivating customer
    @Transactional
    public String setCustomerInactive(Long customerId) throws InvalidEntityException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            if (customer.getConnectionStatus() == ConnectionStatus.INACTIVE) {
                throw new InvalidEntityException("Customer is already inactive");
            }

            customer.setConnectionStatus(ConnectionStatus.INACTIVE);
            customerRepository.save(customer);
            return "Customer status set to inactive successfully.";
        } else {
            throw new InvalidEntityException("Customer not found with ID: " + customerId);
        }
    }




}
