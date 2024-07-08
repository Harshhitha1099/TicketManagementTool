package com.vis.crm.userdetails;

import com.vis.crm.auditing.AuditResponseDTO;
import com.vis.crm.entitystatus.EntityStatus;
import com.vis.crm.usertype.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String title;

    private String username;

    private String password;

    private String phoneNumber;

    private String emailId;

    private EntityStatus entityStatus;

    private UserType userType;

    private AuditResponseDTO createdBy;

    private AuditResponseDTO modifiedBy;

    private Date modifiedAt;

    private Date createdAt;


}
