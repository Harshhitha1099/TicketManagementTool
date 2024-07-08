package com.vis.crm.userdetails;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String title;

    @NotNull(message = "username is required")
    private String username;

    @NotNull(message = "password is required")
    private String password;

    private String phoneNumber;

    @NotNull(message = "emailId is required")
    private String emailId;

    private Long entityStatusId;

    private Long userTypeId;




}
