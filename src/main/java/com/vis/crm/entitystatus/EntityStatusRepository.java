package com.vis.crm.entitystatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityStatusRepository extends JpaRepository<EntityStatus, Long> {
}