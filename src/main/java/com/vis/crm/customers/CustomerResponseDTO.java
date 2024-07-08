package com.vis.crm.customers;

import com.vis.crm.auditing.AuditResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

    private Long customerId;

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "type is required")
    private String type; // e.g., Individual, Company

    private String industry;

    @NotNull(message = "email is required")
    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private String website;

    private AuditResponseDTO createdBy;

    private AuditResponseDTO modifiedBy;

    private Date modifiedAt;

    private Date createdAt;
}
