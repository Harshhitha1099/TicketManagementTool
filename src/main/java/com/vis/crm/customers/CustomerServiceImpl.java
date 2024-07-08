package com.vis.crm.customers;

import com.vis.crm.auditing.AuditResponseDTO;
import com.vis.crm.exception.NotFoundException;
import com.vis.crm.userdetails.UserDetails;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerResponseDTO> findAll() {
        List<Customer> customerDetailsList = customerRepository.findAll();
        return customerDetailsList.stream()
                .map(this::convertToCustomerResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO findById(Long id) {

        return customerRepository.findById(id)
                .map(this::convertToCustomerResponseDTO)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));

    }

    @Override
    public CustomerResponseDTO save(CustomerRequestDTO customerRequestDTO) {

        Customer customer = convertDTOToCustomerDetails(customerRequestDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerResponseDTO customerResponseDTO = convertToCustomerResponseDTO(savedCustomer);
        return customerResponseDTO;
    }

    @Override
    public void deleteById(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with id: " + id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete customer with id: " + id, e);
        }
    }

    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerRequestDTO) {

        Customer existingcustomer= customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));

        existingcustomer.setEmail(customerRequestDTO.getEmail());
        existingcustomer.setIndustry(customerRequestDTO.getIndustry());
        existingcustomer.setName(customerRequestDTO.getName());
        existingcustomer.setPhone(customerRequestDTO.getPhone());
        existingcustomer.setPostalCode(customerRequestDTO.getPostalCode());
        existingcustomer.setState(customerRequestDTO.getState());
        existingcustomer.setType(customerRequestDTO.getType());
        existingcustomer.setWebsite(customerRequestDTO.getWebsite());
        existingcustomer.setAddress(customerRequestDTO.getAddress());
        existingcustomer.setCity(customerRequestDTO.getCity());
        existingcustomer.setCountry(customerRequestDTO.getCountry());

        Customer savedCustomer = customerRepository.save(existingcustomer);

        CustomerResponseDTO customerResponseDTO =convertToCustomerResponseDTO(savedCustomer);

        return  customerResponseDTO;

    }


    private Customer convertDTOToCustomerDetails(CustomerRequestDTO customerRequestDTO) {

        Customer customer = new Customer();
        //customer.setCustomerId(customerRequestDTO.getCustomerId());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setIndustry(customerRequestDTO.getIndustry());
        customer.setName(customerRequestDTO.getName());
        customer.setPhone(customerRequestDTO.getPhone());
        customer.setPostalCode(customerRequestDTO.getPostalCode());
        customer.setState(customerRequestDTO.getState());
        customer.setType(customerRequestDTO.getType());
        customer.setWebsite(customerRequestDTO.getWebsite());
        customer.setAddress(customerRequestDTO.getAddress());
        customer.setCity(customerRequestDTO.getCity());
        customer.setCountry(customerRequestDTO.getCountry());

        return customer;
    }

    public CustomerResponseDTO convertToCustomerResponseDTO(Customer customer) {

        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setEmail(customer.getEmail());
        customerResponseDTO.setIndustry(customer.getIndustry());
        customerResponseDTO.setName(customer.getName());
        customerResponseDTO.setPhone(customer.getPhone());
        customerResponseDTO.setPostalCode(customer.getPostalCode());
        customerResponseDTO.setState(customer.getState());
        customerResponseDTO.setType(customer.getType());
        customerResponseDTO.setWebsite(customer.getWebsite());
        customerResponseDTO.setModifiedAt(customer.getModifiedDate());
        customerResponseDTO.setCreatedAt(customer.getCreatedDate());
        customerResponseDTO.setCustomerId(customer.getCustomerId());
        customerResponseDTO.setAddress(customer.getAddress());
        customerResponseDTO.setCity(customer.getCity());
        customerResponseDTO.setCountry(customer.getCountry());


        if (customer.getCreatedBy() != null) {
            customerResponseDTO.setCreatedBy(convertToUserModifiedAndCreated(customer.getCreatedBy()));
        }

        if (customer.getModifiedBy() != null) {
            customerResponseDTO.setModifiedBy(convertToUserModifiedAndCreated(customer.getModifiedBy()));
        }

        return customerResponseDTO;
    }

    private AuditResponseDTO convertToUserModifiedAndCreated(UserDetails userDetails) {
        AuditResponseDTO dto = new AuditResponseDTO();
        dto.setId(userDetails.getId());
        dto.setUsername(userDetails.getUsername());
        return dto;
    }

}

