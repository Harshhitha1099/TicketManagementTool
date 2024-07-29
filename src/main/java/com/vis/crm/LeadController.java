package com.vis.crm.leads;


import com.vis.crm.model.AResponse;
import com.vis.crm.model.FailureResponse;
import com.vis.crm.model.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/leads")
public class LeadController {
    @Autowired
    private LeadService leadService;

    @GetMapping
    public ResponseEntity<AResponse> getAllLeads() {
        try {
            List<Leads> leads = leadService.getAllLeads();
            SuccessResponse<List<Leads>> response = new SuccessResponse<>(leads, "Leads retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Error retrieving leads");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AResponse> getLeadById(@PathVariable Long id) {
        try {
            Leads lead = leadService.getLeadById(id);
            if (lead != null) {
                SuccessResponse<Leads> response = new SuccessResponse<>(lead, "Leads retrieved successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                FailureResponse response = new FailureResponse("Leads not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Error retrieving lead");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<AResponse> createLead(@RequestBody Leads lead) {
        try {
            Leads savedLead = leadService.saveLead(lead);
            SuccessResponse<Leads> response = new SuccessResponse<>(savedLead, "Leads created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Error creating lead");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AResponse> updateLead(@PathVariable Long id, @RequestBody Leads lead) {
        try {
            Leads existingLead = leadService.getLeadById(id);
            if (existingLead != null) {
                lead.setLeadId(id);
                Leads updatedLead = leadService.saveLead(lead);
                 SuccessResponse<Leads> response = new SuccessResponse<>(updatedLead, "Leads updated successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                FailureResponse response = new FailureResponse("Leads not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Error updating lead");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AResponse> deleteLead(@PathVariable Long id) {
        try {
            Leads existingLead = leadService.getLeadById(id);
            if (existingLead != null) {
                leadService.deleteLead(id);
                SuccessResponse<Void> response = new SuccessResponse<>("Leads deleted successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                FailureResponse response = new FailureResponse("Leads not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            FailureResponse response = new FailureResponse(ex, "Error deleting lead");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}