package com.vis.crm.usertype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserTypeServiceImpl  implements UserTypeService {

    @Autowired
    public UserTypeRepository userTypeRepository;

    // getAllUserTypes() method fetches all the usertypes present in DB
    @Override
    public List<UserType> getAllUserTypes() {
        return userTypeRepository.findAll();
    }
}
