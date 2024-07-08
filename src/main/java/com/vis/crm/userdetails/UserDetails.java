package com.vis.crm.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vis.crm.auditing.AuditMetadata;
import com.vis.crm.entitystatus.EntityStatus;
import com.vis.crm.usertype.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_details")
@EntityListeners(AuditingEntityListener.class)
public class UserDetails extends AuditMetadata implements org.springframework.security.core.userdetails.UserDetails {  //users or user_details

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotNull(message = "Entity status is required")
    @ManyToOne
    @JoinColumn(name = "entityStatus")
    private EntityStatus entityStatus;

    @NotNull(message = "UserDetails type is required")
    @ManyToOne
    @JoinColumn(name = "userType")
    private UserType userType;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }
}
