package sjsu.cmpe275.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.service.EmployeeService;
import sjsu.cmpe275.entity.Employee;

@RestController
@Transactional
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value="/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> testFunc() {
        return ResponseEntity.status(HttpStatus.OK).body("Test API");
    }

    // Implement the required REST API methods, e.g., createEmployee, getEmployee, etc.
    @PostMapping(value = "/create/{employerId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employee> createEmployee(
            @RequestBody ObjectNode body,
            @PathVariable("employerId") long employerId,
            @RequestParam(value = "format", defaultValue = "json") String format) throws JsonProcessingException {

        Employee newEmployee = employeeService.createEmployee(body, employerId);

            if (newEmployee == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(newEmployee);
    }
}
