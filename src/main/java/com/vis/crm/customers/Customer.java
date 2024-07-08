package com.vis.crm.customers;

import com.vis.crm.auditing.AuditMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Customer extends AuditMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
