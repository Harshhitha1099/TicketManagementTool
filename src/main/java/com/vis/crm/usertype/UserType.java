package com.vis.crm.usertype;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    //ADMIN,USER,SUPERVISOR,SUPERUSER

}
