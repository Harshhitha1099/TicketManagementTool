package com.vis.crm.contacts;

import com.vis.crm.exception.NotFoundException;
import com.vis.crm.model.AResponse;
import com.vis.crm.model.FailureResponse;
import com.vis.crm.model.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<AResponse> getAllContacts() {
        try {
            List<ContactResponseDTO> contacts = contactService.findAll();
            SuccessResponse<List<ContactResponseDTO>> response = new SuccessResponse<>(contacts, "Contacts retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to retrieve contacts");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<AResponse> getContactById(@PathVariable Long id) {
        try {
            ContactResponseDTO contact = contactService.findById(id);
            SuccessResponse<ContactResponseDTO> response = new SuccessResponse<>(contact, "Contact retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            FailureResponse response = new FailureResponse(ex, "Contact not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AResponse> createContact(@RequestBody ContactRequestDTO contactRequestDTO) {
        try {
            ContactResponseDTO contact = contactService.save(contactRequestDTO);
            SuccessResponse<ContactResponseDTO> response = new SuccessResponse<>(contact, "Contact created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to create contact");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AResponse> updateContact(@PathVariable Long id, @RequestBody ContactRequestDTO contactRequestDTO) {
        try {
            ContactResponseDTO updatedContact = contactService.updateContact(id, contactRequestDTO);
            SuccessResponse<ContactResponseDTO> response = new SuccessResponse<>(updatedContact, "Contact updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            FailureResponse response = new FailureResponse(ex, "Contact not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AResponse> deleteContact(@PathVariable Long id) {
        try {
            contactService.deleteById(id);
            SuccessResponse<String> response = new SuccessResponse<>("Contact deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            if (ex.getStatus() == HttpStatus.NOT_FOUND) {
                FailureResponse response = new FailureResponse(ex, "Contact not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                FailureResponse response = new FailureResponse(ex, "Failed to delete contact: " + ex.getMessage());
                return new ResponseEntity<>(response, ex.getStatus());
            }
        } catch (Exception e) {
            FailureResponse response = new FailureResponse(e, "Failed to delete contact: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

