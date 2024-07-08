package com.vis.crm.userdetails;

import com.vis.crm.auditing.AuditResponseDTO;
import com.vis.crm.entitystatus.EntityStatus;
import com.vis.crm.exception.NotFoundException;
import com.vis.crm.usertype.UserType;
import com.vis.crm.entitystatus.EntityStatusRepository;
import com.vis.crm.usertype.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceCRMImpl implements UserServiceCRM {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityStatusRepository entityStatusRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    // getAllUsers() fetches the all users from DB
    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<UserDetails> userDetailsList = userRepository.findAll();

        // Convert each UserDetails entity to a UserResponseDTO and collect into a list
        return userDetailsList.stream()
                .map(this::convertToUserResponseDTO)
                .collect(Collectors.toList());
    }

    //getUserById() fetches the users based on userId
    @Override
    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToUserResponseDTO)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    //createUser method creates the new user by taking the all details
    @Override
    public UserResponseDTO createUser(UserRequestDTO  userRequestDTO) {
        UserDetails userCRMDetails = convertDTOToUserDetails(userRequestDTO);
        UserDetails savedUser = userRepository.save(userCRMDetails);
        UserResponseDTO userResponseDTO = convertToUserResponseDTO(savedUser);
        return userResponseDTO;
    }

    //updateUser method updates the existing user
    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userCRMDetails){

        UserDetails existingUserCRM = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));


        existingUserCRM.setFirstName(userCRMDetails.getFirstName());
        existingUserCRM.setMiddleName(userCRMDetails.getMiddleName());
        existingUserCRM.setLastName(userCRMDetails.getLastName());
        existingUserCRM.setTitle(userCRMDetails.getTitle());
        existingUserCRM.setUsername(userCRMDetails.getUsername());
        existingUserCRM.setPassword(userCRMDetails.getPassword());
        existingUserCRM.setPhoneNumber(userCRMDetails.getPhoneNumber());
        existingUserCRM.setEmailId(userCRMDetails.getEmailId());

        if (userCRMDetails.getEntityStatusId() != null) {
            EntityStatus entityStatus = entityStatusRepository.findById(userCRMDetails.getEntityStatusId())
                    .orElseThrow(() -> new IllegalArgumentException("EntityStatus not found with ID: " + userCRMDetails.getEntityStatusId()));
            existingUserCRM.setEntityStatus(entityStatus);
        }

        if(userCRMDetails.getUserTypeId() !=null) {
            UserType userType = userTypeRepository.findById(userCRMDetails.getUserTypeId())
                    .orElseThrow(() -> new RuntimeException("UserType not found"));
            existingUserCRM.setUserType(userType);
        }

        UserDetails savedUser = userRepository.save(existingUserCRM);

        UserResponseDTO userResponseDTO =convertToUserResponseDTO(savedUser);

        return userResponseDTO;
    }

    //deleteUser(Long id)  method deleted the user from the DB for which userId passed
    @Override
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete user with id: " + id, e);
        }
    }

    // partialUpdate method updates the existing user with the fields
    @Override
    public UserResponseDTO partialUpdate(Long id, Map<String, Object> updates) {

        UserDetails existingUserCRM = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    existingUserCRM.setFirstName((String) value);
                    break;
                case "middleName":
                    existingUserCRM.setMiddleName((String) value);
                    break;
                case "lastName":
                    existingUserCRM.setLastName((String) value);
                    break;
                case "title":
                    existingUserCRM.setTitle((String) value);
                    break;
                case "username":
                    existingUserCRM.setUsername((String) value);
                    break;
                case "password":
                    existingUserCRM.setPassword((String) value);
                    break;
                case "phoneNumber":
                    existingUserCRM.setPhoneNumber((String) value);
                    break;
                case "emailId":
                    existingUserCRM.setEmailId((String) value);
                    break;
                case "entityStatusId":
                    if (value instanceof Integer) {
                        EntityStatus entityStatus = entityStatusRepository.findById(((Integer) value).longValue())
                                .orElseThrow(() -> new RuntimeException("EntityStatus not found"));
                        existingUserCRM.setEntityStatus(entityStatus);
                    } else if (value instanceof Long) {
                        EntityStatus entityStatus = entityStatusRepository.findById((Long) value)
                                .orElseThrow(() -> new RuntimeException("EntityStatus not found"));
                        existingUserCRM.setEntityStatus(entityStatus);
                    } else if (value instanceof String) {
                        EntityStatus entityStatus = entityStatusRepository.findById(Long.parseLong((String) value))
                                .orElseThrow(() -> new RuntimeException("EntityStatus not found"));
                        existingUserCRM.setEntityStatus(entityStatus);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'entityStatusId' field.");
                    }
                    break;
                case "userTypeId" :
                    if (value instanceof Integer) {
                        UserType userType = userTypeRepository.findById(((Integer) value).longValue())
                                .orElseThrow(() -> new RuntimeException("UserType not found"));
                        existingUserCRM.setUserType(userType);
                    } else if (value instanceof Long) {
                        UserType userType = userTypeRepository.findById((Long) value)
                                .orElseThrow(() -> new RuntimeException("UserType not found"));
                        existingUserCRM.setUserType(userType);
                    } else if (value instanceof String) {
                        UserType userType = userTypeRepository.findById(Long.parseLong((String) value))
                                .orElseThrow(() -> new RuntimeException("UserType not found"));
                        existingUserCRM.setUserType(userType);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'userTypeId' field.");
                    }
                    break;
                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });
        UserDetails savedUser = userRepository.save(existingUserCRM);

        UserResponseDTO userResponseDTO =convertToUserResponseDTO(savedUser);

        return userResponseDTO;
    }

    //getUserByEntityStatus method gets the user from the entity status
    @Override
    public List<UserResponseDTO> getUserByEntityStatus(String status) {
        List<UserDetails> userDetailsList = userRepository.findByEntityStatusStatus(status);

        // Convert each UserDetails entity to a UserResponseDTO and collect into a list
        return userDetailsList.stream()
                .map(this::convertToUserResponseDTO)
                .collect(Collectors.toList());
    }

    //getUserByUserType method provides the user details based on the usertype
    @Override
    public List<UserResponseDTO> getUserByUserType(String type) {
        List<UserDetails> userDetailsList = userRepository.findByUserTypeType(type);

        // Convert each UserDetails entity to a UserResponseDTO and collect into a list
        return userDetailsList.stream()
                .map(this::convertToUserResponseDTO)
                .collect(Collectors.toList());
    }

    //getUserByEntityStatusandUserType method fetches the all the user based on entity status and usertype
    @Override
    public List<UserResponseDTO> getUserByEntityStatusandUserType(String status, String type) {
        List<UserDetails> userDetailsList = userRepository.findByEntityStatusStatusAndUserTypeType(status,type);

        // Convert each UserDetails entity to a UserResponseDTO and collect into a list
        return userDetailsList.stream()
                .map(this::convertToUserResponseDTO)
                .collect(Collectors.toList());
    }

    private UserDetails convertDTOToUserDetails(UserRequestDTO userCRMDetails) {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmailId(userCRMDetails.getEmailId());
        userDetails.setFirstName(userCRMDetails.getFirstName());
        userDetails.setLastName(userCRMDetails.getLastName());
        userDetails.setMiddleName(userCRMDetails.getMiddleName());
        userDetails.setId(userCRMDetails.getId());
        userDetails.setTitle(userCRMDetails.getTitle());
        userDetails.setUsername(userCRMDetails.getUsername());
        userDetails.setPhoneNumber(userCRMDetails.getPhoneNumber());
        userDetails.setPassword(userCRMDetails.getPassword());

        if (userCRMDetails.getEntityStatusId() != null) {
            EntityStatus entityStatus = entityStatusRepository.findById(userCRMDetails.getEntityStatusId())
                    .orElseThrow(() -> new IllegalArgumentException("EntityStatus not found with ID: " + userCRMDetails.getEntityStatusId()));
            userDetails.setEntityStatus(entityStatus);
        }

        if(userCRMDetails.getUserTypeId() != null) {
            UserType userType = userTypeRepository.findById(userCRMDetails.getUserTypeId())
                    .orElseThrow(() -> new RuntimeException("UserType not found"));
            userDetails.setUserType(userType);
        }

        return userDetails;
    }

    public UserResponseDTO convertToUserResponseDTO(UserDetails userDetails) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userDetails.getId());
        userResponseDTO.setFirstName(userDetails.getFirstName());
        userResponseDTO.setMiddleName(userDetails.getMiddleName());
        userResponseDTO.setLastName(userDetails.getLastName());
        userResponseDTO.setTitle(userDetails.getTitle());
        userResponseDTO.setUsername(userDetails.getUsername());
        userResponseDTO.setPassword(userDetails.getPassword());
        userResponseDTO.setPhoneNumber(userDetails.getPhoneNumber());
        userResponseDTO.setEmailId(userDetails.getEmailId());
        userResponseDTO.setEntityStatus(userDetails.getEntityStatus());
        userResponseDTO.setUserType(userDetails.getUserType());
        userResponseDTO.setCreatedAt(userDetails.getCreatedDate());
        userResponseDTO.setModifiedAt(userDetails.getModifiedDate());

        if (userDetails.getCreatedBy() != null) {
            userResponseDTO.setCreatedBy(convertToUserModifiedAndCreated(userDetails.getCreatedBy()));
        }

        if (userDetails.getModifiedBy() != null) {
            userResponseDTO.setModifiedBy(convertToUserModifiedAndCreated(userDetails.getModifiedBy()));
        }
        if (userDetails.getCreatedBy() != null) {
            userResponseDTO.setCreatedBy(convertToUserModifiedAndCreated(userDetails.getCreatedBy()));
        }

        if (userDetails.getModifiedBy() != null) {
            userResponseDTO.setModifiedBy(convertToUserModifiedAndCreated(userDetails.getModifiedBy()));
        }

        return userResponseDTO;
    }

    private AuditResponseDTO convertToUserModifiedAndCreated(UserDetails userDetails) {
        AuditResponseDTO dto = new AuditResponseDTO();
        dto.setId(userDetails.getId());
        dto.setUsername(userDetails.getUsername());
        return dto;
    }



}
