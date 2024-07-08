package com.vis.crm.userdetails;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetails, Long> {
    List<UserDetails> findByEntityStatusStatus(String status);

    List<UserDetails> findByUserTypeType(String type);

    List<UserDetails> findByEntityStatusStatusAndUserTypeType(String status, String type);
    Optional<UserDetails>  findByUsername(String username);

}
