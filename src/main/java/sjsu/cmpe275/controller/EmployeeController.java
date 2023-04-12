package sjsu.cmpe275.controller;

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
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value="/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> testFunc() {
        return ResponseEntity.status(HttpStatus.OK).body("Test API");
    }

    // Implement the required REST API methods, e.g., createEmployee, getEmployee, etc.
    @PostMapping("employee/create/{employerId}")
    public ResponseEntity<Employee> createEmployee(
            @PathVariable("employerId") long employerId,
            @RequestParam("name") String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "zip", required = false) String zip,
            @RequestParam(value = "managerId", required = false) long managerId,
            @RequestParam(value = "format", defaultValue = "json") String format) {

        Employee newEmployee = employeeService.createEmployee(employerId, name, email, title, street, city, state, zip, managerId);

        if (newEmployee == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(newEmployee);
    }
}
