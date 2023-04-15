package sjsu.cmpe275.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.service.CollaboratorService;

@RestController
@RequestMapping(value = "/collaborators")
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    @PutMapping("/{employerId1}/{employeeId1}/{employerId2}/{employeeId2}")
    public ResponseEntity<String> addCollaborator(
            @PathVariable("employerId1") long employerId1,
            @PathVariable("employeeId1") long employeeId1,
            @PathVariable("employerId2") long employerId2,
            @PathVariable("employeeId2") long employeeId2) throws Exception {

        try {
            Boolean success = collaboratorService.addCollaborator(employerId1, employeeId1, employerId2, employeeId2);
            if(!success) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collaboration already exists!");

            return ResponseEntity.ok().body("Collaborator added successfully.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{employerId1}/{employeeId1}/{employerId2}/{employeeId2}")
    public ResponseEntity<String> deleteCollaborator(
            @PathVariable("employerId1") long employerId1,
            @PathVariable("employeeId1") long employeeId1,
            @PathVariable("employerId2") long employerId2,
            @PathVariable("employeeId2") long employeeId2) throws Exception {

        try {
            Boolean success = collaboratorService.deleteCollaborator(employerId1, employeeId1, employerId2, employeeId2);
            if(!success) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee or Collaboration does not exists!");

            return ResponseEntity.ok().body("Collaborator deleted successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
