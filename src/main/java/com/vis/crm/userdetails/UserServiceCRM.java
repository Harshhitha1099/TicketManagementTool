package com.vis.crm.userdetails;

import java.util.List;
import java.util.Map;

public interface UserServiceCRM {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO createUser(UserRequestDTO userrequestDTO);
    UserResponseDTO updateUser(Long id, UserRequestDTO userrequestDTO);
    void deleteUser(Long id);
    UserResponseDTO partialUpdate(Long id, Map<String, Object> updates);

    List<UserResponseDTO> getUserByEntityStatus(String status);

    List<UserResponseDTO> getUserByUserType(String type);

    List<UserResponseDTO> getUserByEntityStatusandUserType(String status, String type);
}
