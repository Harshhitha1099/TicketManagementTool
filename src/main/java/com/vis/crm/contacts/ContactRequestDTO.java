package com.vis.crm.contacts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequestDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private String firstName;

    private String lastName;

    private String role;

    private String email;

    private String phone;

    private String mobile;

    private String address;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}
