package com.vis.crm.contacts;

import com.vis.crm.auditing.AuditResponseDTO;
import com.vis.crm.customers.Customer;
import com.vis.crm.customers.CustomerRepository;
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
public class ContactServiceImpl implements ContactService {

    @Autowired
    private  ContactRepository contactRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<ContactResponseDTO> findAll() {
        List<Contact> contactDetailsList = contactRepository.findAll();
        return contactDetailsList.stream()
                .map(this::convertToContactResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContactResponseDTO findById(Long id) {

        return contactRepository.findById(id)
                .map(this::convertToContactResponseDTO)
                .orElseThrow(() -> new NotFoundException("Contact not found with ID: " + id));

    }

    @Override
    public ContactResponseDTO save(ContactRequestDTO contactRequestDTO) {
        Contact contact = convertDTOToContactDetails(contactRequestDTO);
        Contact savedContact = contactRepository.save(contact);
        ContactResponseDTO contactResponseDTO = convertToContactResponseDTO(savedContact);
        return  contactResponseDTO;
    }

    @Override
    public void deleteById(Long id) {
        try {
            contactRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found with id: " + id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete contact with id: " + id, e);
        }
    }

    @Override
    public ContactResponseDTO updateContact(Long id, ContactRequestDTO contactRequestDTO) {


       Contact existingcontact= contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact not found with ID: " + id));

       existingcontact.setAddress(contactRequestDTO.getAddress());
       existingcontact.setCity(contactRequestDTO.getCity());
       existingcontact.setCountry(contactRequestDTO.getCountry());
       existingcontact.setEmail(contactRequestDTO.getEmail());
       existingcontact.setFirstName(contactRequestDTO.getFirstName());
       existingcontact.setLastName(contactRequestDTO.getLastName());
       existingcontact.setMobile(contactRequestDTO.getMobile());
       existingcontact.setPhone(contactRequestDTO.getPhone());
       existingcontact.setPostalCode(contactRequestDTO.getPostalCode());
       existingcontact.setRole(contactRequestDTO.getRole());
       existingcontact.setState(contactRequestDTO.getState());

       Long customerId = contactRequestDTO.getCustomerId();
       Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Invalid customer ID"));

       existingcontact.setCustomer(customer);

       Contact savedContact = contactRepository.save(existingcontact);

       ContactResponseDTO contactResponseDTO =convertToContactResponseDTO(savedContact);

       return  contactResponseDTO;
    }


    private Contact convertDTOToContactDetails(ContactRequestDTO contactRequestDTO) {

        Contact contact = new Contact();

        contact.setAddress(contactRequestDTO.getAddress());
        contact.setCity(contactRequestDTO.getCity());
        contact.setCountry(contactRequestDTO.getCountry());
        contact.setEmail(contactRequestDTO.getEmail());
        contact.setFirstName(contactRequestDTO.getFirstName());
        contact.setLastName(contactRequestDTO.getLastName());
        contact.setMobile(contactRequestDTO.getMobile());
        contact.setPhone(contactRequestDTO.getPhone());
        contact.setPostalCode(contactRequestDTO.getPostalCode());
        contact.setRole(contactRequestDTO.getRole());
        contact.setState(contactRequestDTO.getState());

        Long customerId = contactRequestDTO.getCustomerId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Invalid customer ID"));

        contact.setCustomer(customer);

        return contact;
    }

    public ContactResponseDTO convertToContactResponseDTO(Contact contact) {

        ContactResponseDTO contactResponseDTO = new ContactResponseDTO();

        contactResponseDTO.setContactId(contact.getContactId());
        contactResponseDTO.setAddress(contact.getAddress());
        contactResponseDTO.setCity(contact.getCity());
        contactResponseDTO.setCountry(contact.getCountry());
        contactResponseDTO.setCreatedAt(contact.getCreatedDate());
        contactResponseDTO.setEmail(contact.getEmail());
        contactResponseDTO.setFirstName(contact.getFirstName());
        contactResponseDTO.setLastName(contact.getLastName());
        contactResponseDTO.setMobile(contact.getMobile());
        contactResponseDTO.setModifiedAt(contact.getModifiedDate());
        contactResponseDTO.setPhone(contact.getPhone());
        contactResponseDTO.setRole(contact.getRole());
        contactResponseDTO.setState(contact.getState());

        if (contact.getCreatedBy() != null) {
            contactResponseDTO.setCreatedBy(convertToUserModifiedAndCreated(contact.getCreatedBy()));
        }

        if (contact.getModifiedBy() != null) {
            contactResponseDTO.setModifiedBy(convertToUserModifiedAndCreated(contact.getModifiedBy()));
        }

        if(contact.getCustomer()!=null) {
            contactResponseDTO.setCustomer(convertToContactCustomerInfo(contact.getCustomer()));
        }

        return contactResponseDTO;
    }

    private AuditResponseDTO convertToUserModifiedAndCreated(UserDetails userDetails) {
        AuditResponseDTO dto = new AuditResponseDTO();
        dto.setId(userDetails.getId());
        dto.setUsername(userDetails.getUsername());
        return dto;
    }

    private ContactCustomerDTO convertToContactCustomerInfo(Customer customer) {
        ContactCustomerDTO dto = new  ContactCustomerDTO();
        dto.setId(customer.getCustomerId());
        dto.setCustomerName(customer.getName());
        return dto;
    }


}
