package com.vis.crm.entitystatus;

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
@RequestMapping("api/entitystatus")
public class EntityStatusController {

    @Autowired
    public EntityStatusService entityStatusService;

    // API to fetch all the entitystatus present in DB
    @GetMapping
    public ResponseEntity<AResponse> getAllEntityStatus() {
        try {
            List<EntityStatus> entityStatuses = entityStatusService.getAllEntityStatus();
            SuccessResponse<List<EntityStatus>> response = new SuccessResponse<>(entityStatuses, "Entity statuses retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Failed to retrieve entity statuses");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
