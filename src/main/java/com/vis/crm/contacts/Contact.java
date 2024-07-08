package com.vis.crm.contacts;

import com.vis.crm.auditing.AuditMetadata;
import com.vis.crm.customers.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contact extends AuditMetadata {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @NotNull
    private String firstName;

    private String lastName;

    private String role;

    private String email;

    @NotNull
    private String phone;

    private String mobile;

    private String address;

    private String city;

    private String state;

    private String country;

    private String postalCode;

}
