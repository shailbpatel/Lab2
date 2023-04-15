package sjsu.cmpe275.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.service.EmployeeService;
import sjsu.cmpe275.entity.Employee;
import sjsu.cmpe275.entity.EmployeeId;

@RestController
@Transactional
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    // Implement the required REST API methods, e.g., createEmployee, getEmployee, etc.
    @PostMapping(value = "/create/{employerId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employee> createEmployee(
            @RequestParam("name") String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "zip", required = false) String zip,
            @RequestParam(value = "managerId", required = false) Long managerId,
            @PathVariable("employerId") long employerId,
            @RequestParam(value = "format", defaultValue = "json") String format) throws Exception {

        Employee newEmployee = employeeService.createEmployee(name, email, title, street, city, state, zip, managerId, employerId);

            if (newEmployee == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        if ("json".equalsIgnoreCase(format)) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        } else if ("xml".equalsIgnoreCase(format)) {
            headers.setContentType(MediaType.APPLICATION_XML);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(newEmployee);
    }
}
