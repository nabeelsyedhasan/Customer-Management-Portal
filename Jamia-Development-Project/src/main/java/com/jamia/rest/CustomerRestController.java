package com.jamia.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jamia.bean.Customer;
import com.jamia.dao.*;
import java.util.List;

@RestController
public class CustomerRestController {
    private final CustomerDao customerDao;

    @Autowired
    public CustomerRestController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return customerDao.GetCustomerById(id);
    }

    @PostMapping(value = "/customers")
    public ResponseEntity createCustomer(@RequestBody Customer customer) {

		customerDao.create(customer);

		return new ResponseEntity(customer, HttpStatus.OK);
	}

    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer existingCustomer = customerDao.GetCustomerById(id);
        
        if (existingCustomer == null) {
            return new ResponseEntity<>("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
        }

        // Update the existing customer object with the new values
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setCustomerAddress(customer.getCustomerAddress());
        // Update other fields as necessary

        // Perform the update operation
        customerDao.update(existingCustomer);

        return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
    }


    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        customerDao.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}