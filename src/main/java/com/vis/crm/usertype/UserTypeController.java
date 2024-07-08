package com.vis.crm.usertype;

import com.vis.crm.model.AResponse;
import com.vis.crm.model.FailureResponse;
import com.vis.crm.model.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/userType")
public class UserTypeController {
    @Autowired
    public UserTypeService userTypeService;

    // API to fetch all the usertypes present in DB

    @GetMapping()
    public ResponseEntity<AResponse> getAllUserTypes() {
        try {
            List<UserType> userTypes = userTypeService.getAllUserTypes();
            SuccessResponse<List<UserType>> response = new SuccessResponse<>(userTypes, "User types retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to retrieve user types");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
