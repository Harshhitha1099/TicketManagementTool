package com.vis.crm.userdetails;

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
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceCRM userService;

    // API Fetchs user details for providing user id
    @GetMapping("/{id}")
    public ResponseEntity<AResponse> getUserById(@PathVariable Long id) {
        try {
            UserResponseDTO user= userService.getUserById(id);
            SuccessResponse<UserResponseDTO> response = new SuccessResponse<>(user, "User retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            FailureResponse response = new FailureResponse(ex, "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "An error occurred while retrieving the user");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //API creates new user
    @PostMapping
    public ResponseEntity<AResponse> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO createdUser = userService.createUser(userRequestDTO);
            SuccessResponse<UserResponseDTO> response = new SuccessResponse<>(createdUser, "User created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to create user");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //API Updates the existing user by specifing user id
    @PutMapping("/{id}")
    public ResponseEntity<AResponse> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
            SuccessResponse<UserResponseDTO> response = new SuccessResponse<>(updatedUser, "User updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            FailureResponse response = new FailureResponse(ex, "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to update user");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //API Deletes the existing user by specifing user id
    @DeleteMapping("/{id}")
    public ResponseEntity<AResponse> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            SuccessResponse<String> response = new SuccessResponse<>("User deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            if (ex.getStatus() == HttpStatus.NOT_FOUND) {
                FailureResponse response = new FailureResponse(ex, "User not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                FailureResponse response = new FailureResponse(ex, "Failed to delete user: " + ex.getMessage());
                return new ResponseEntity<>(response, ex.getStatus());
            }
        } catch (Exception e) {
            FailureResponse response = new FailureResponse(e, "Failed to delete user: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //API updates the exiting user by taking userid and any specific fields mentioned as request body
    @PatchMapping("/{id}")
    public ResponseEntity<AResponse> partialUpdateUser(@PathVariable Long id,
                                                       @RequestBody Map<String, Object> updates) {
        try {
            UserResponseDTO updatedUserCRM = userService.partialUpdate(id, updates);
            SuccessResponse<UserResponseDTO> response = new SuccessResponse<>(updatedUserCRM, "User partially updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException ex) {
            FailureResponse response = new FailureResponse(ex, "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to partially update user");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     API
     - Fetchs all user details present in DB
     - provides the user details based on entity status passed
     - provides the user details based on UserDetails Type passed
     - provides the user details based on entity status and userType passed
     */
    @GetMapping
    public ResponseEntity<AResponse> getUsersByStatusAndType(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "type", required = false) String type
    ) {
        try {
            List<UserResponseDTO> users;
            if (status != null && type != null) {
                users = userService.getUserByEntityStatusandUserType(status, type);
            } else if (status != null) {
                users = userService.getUserByEntityStatus(status);
            } else if (type != null) {
                users = userService.getUserByUserType(type);
            } else {
                users = userService.getAllUsers();
            }
            SuccessResponse<List<UserResponseDTO>> response = new SuccessResponse<>(users, "Users retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to retrieve users");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}
