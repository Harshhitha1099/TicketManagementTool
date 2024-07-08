package com.vis.crm.contacts;

import java.util.List;

public interface ContactService {

    List<ContactResponseDTO> findAll();
    ContactResponseDTO findById(Long id);
    ContactResponseDTO save(ContactRequestDTO contactRequestDTO);
    void deleteById(Long id);

    ContactResponseDTO updateContact(Long id,ContactRequestDTO  contactRequestDTO);
}
