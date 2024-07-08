package com.vis.crm.entitystatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EntityStatusServiceImpl  implements EntityStatusService {

    @Autowired
    public EntityStatusRepository entityStatusRepository;

    // getAllEntityStatus()  method fetches all the entity status present in DB
    @Override
    public List<EntityStatus> getAllEntityStatus() {
        return entityStatusRepository.findAll();
    }

}
