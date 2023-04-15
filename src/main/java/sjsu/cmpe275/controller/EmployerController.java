package sjsu.cmpe275.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.entity.Address;
import sjsu.cmpe275.entity.Employee;
import sjsu.cmpe275.entity.Employer;
import sjsu.cmpe275.service.EmployerService;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    // Implement the required REST API methods, e.g., createEmployer, getEmployer, etc.
    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employer> createEmployer(
            @RequestBody ObjectNode body,
            @RequestParam(value = "format", defaultValue = "json") String format) throws JsonProcessingException {

        Employer newEmployer = employerService.createEmployer(body);

        if (newEmployer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(newEmployer);
    }

}
