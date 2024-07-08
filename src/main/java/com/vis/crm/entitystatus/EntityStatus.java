package com.vis.crm.entitystatus;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class EntityStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

   //ACTIVE,INACTIVE,DELETED

}
