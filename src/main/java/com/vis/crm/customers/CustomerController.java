package com.vis.crm.customers;

import com.vis.crm.exception.NotFoundException;
import com.vis.crm.model.AResponse;
import com.vis.crm.model.FailureResponse;
import com.vis.crm.model.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<AResponse> getAllCustomers() {
        try {
            List<CustomerResponseDTO> customers = customerService.findAll();
            SuccessResponse<List<CustomerResponseDTO>> response = new SuccessResponse<>(customers, "Customers retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to retrieve customers");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AResponse> getCustomerById(@PathVariable Long id) {
        try {
            CustomerResponseDTO customer = customerService.findById(id);
            SuccessResponse<CustomerResponseDTO> response = new SuccessResponse<>(customer, "Customer retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            FailureResponse response = new FailureResponse(ex, "Customer not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AResponse> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        try {
            CustomerResponseDTO customer = customerService.save(customerRequestDTO);
            SuccessResponse<CustomerResponseDTO> response = new SuccessResponse<>(customer, "Customer created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to create customer");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<AResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDTO customerRequestDTO) {
        try {
            CustomerResponseDTO updatedCustomer = customerService.updateCustomer(id, customerRequestDTO);
            SuccessResponse<CustomerResponseDTO> response = new SuccessResponse<>(updatedCustomer, "Customer updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            FailureResponse response = new FailureResponse(ex, "Customer not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AResponse> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteById(id);
            SuccessResponse<String> response = new SuccessResponse<>("Customer deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            if (ex.getStatus() == HttpStatus.NOT_FOUND) {
                FailureResponse response = new FailureResponse(ex, "Customer not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                FailureResponse response = new FailureResponse(ex, "Failed to delete customer: " + ex.getMessage());
                return new ResponseEntity<>(response, ex.getStatus());
            }
        } catch (Exception e) {
            FailureResponse response = new FailureResponse(e, "Failed to delete customer: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

