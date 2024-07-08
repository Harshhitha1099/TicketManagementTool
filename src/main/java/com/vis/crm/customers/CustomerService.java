package com.vis.crm.customers;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseDTO> findAll();

    CustomerResponseDTO findById(Long id);
    CustomerResponseDTO save(CustomerRequestDTO customerRequestDTO);

    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerRequestDTO);
    void deleteById(Long id);
}
