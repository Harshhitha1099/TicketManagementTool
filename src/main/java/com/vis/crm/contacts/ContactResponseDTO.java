package com.vis.crm.contacts;

import com.vis.crm.auditing.AuditResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponseDTO {

    private Long contactId;

    @NotNull
    private ContactCustomerDTO customer;

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

    private AuditResponseDTO createdBy;

    private AuditResponseDTO modifiedBy;

    private Date modifiedAt;

    private Date createdAt;
}
