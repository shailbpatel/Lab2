package sjsu.cmpe275.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sjsu.cmpe275.entity.Employee;
import sjsu.cmpe275.entity.EmployeeId;
import sjsu.cmpe275.entity.Employer;
import sjsu.cmpe275.service.EmployeeService;
import sjsu.cmpe275.service.EmployerService;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    @Autowired
    private EmployeeService employeeService;

    // Implement the required REST API methods, e.g., createEmployer, getEmployer, etc.
    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employer> createEmployer(
            @RequestParam(required = true) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String zip,
            @RequestParam(value = "format", defaultValue = "json") String format ) throws ResponseStatusException {

        Employer newEmployer = employerService.createEmployer(name, description, street, city, state, zip);

        if (newEmployer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if ("json".equalsIgnoreCase(format)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(newEmployer);
        } else if ("xml".equalsIgnoreCase(format)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(newEmployer);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(value = "/{employerId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employer> getEmployer(@PathVariable Long employerId, @RequestParam(value = "format", defaultValue = "json") String format) throws ResponseStatusException {
        Employer employer = employerService.getEmployer(employerId);
        if (format.equals("xml")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(employer);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(employer);
        }
    }

    @PutMapping(path = "/{employerId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Employer> updateEmployer( @PathVariable("employerId") long employerId,
                                                    @RequestParam(required = true) String name,
                                                    @RequestParam(required = false) String description,
                                                    @RequestParam(required = false) String street,
                                                    @RequestParam(required = false) String city,
                                                    @RequestParam(required = false) String state,
                                                    @RequestParam(required = false) String zip,
                                                    @RequestParam(required = false) String format) {

        try {
            Employer employer = employerService.updateEmployer(employerId, name, description, street, city, state, zip);
            if (format != null && format.equals("xml")) {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(employer);
            } else {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(employer);
            }
        } catch (Exception e) {
            if(e.getMessage() == "Employer does not exist!") {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(value = "/{employerId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteEmployer(@PathVariable("employerId") Long employerId, @RequestParam(required = false) String format) throws Exception {
        Employer deletedEmployer = employerService.deleteEmployer(employerId);

        if (deletedEmployer == null) {
            return ResponseEntity.notFound().build();
        }
        if (format != null && format.equals("xml")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(deletedEmployer);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(deletedEmployer);
        }
    }

    @GetMapping(value = "/{employerId}/employee/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employerId, @PathVariable Long id, @RequestParam(value = "format", defaultValue = "json") String format) throws ResponseStatusException {
        EmployeeId employeeId = new EmployeeId(id, employerId);
        Employee employee = employeeService.getEmployee(employeeId);
        if (format.equals("xml")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(employee);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(employee);
        }
    }

    @PutMapping(path = "/{employerId}/employee/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Employee> updateEmployee( @PathVariable("employerId") long employerId,
                                                    @PathVariable("id") long id,
                                                    @RequestParam(required = true) String name,
                                                    @RequestParam(required = false) String email,
                                                    @RequestParam(required = false) String title,
                                                    @RequestParam(required = false) String street,
                                                    @RequestParam(required = false) String city,
                                                    @RequestParam(required = false) String state,
                                                    @RequestParam(required = false) String zip,
                                                    @RequestParam(required = false) Long managerId,
                                                    @RequestParam(required = false) String format) throws ResponseStatusException {

        Employee employee = employeeService.updateEmployee(new EmployeeId(id, employerId), name, email, title, street, city, state, zip, managerId);
        if (format != null && format.equals("xml")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(employee);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(employee);
        }
    }

    @DeleteMapping(value = "/{employerId}/employee/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteEmployee(@PathVariable("employerId") Long employerId, @PathVariable("id") Long id, @RequestParam(required = false) String format) throws ResponseStatusException {
        Employee deletedEmployee = employeeService.deleteEmployee(id, employerId);
        if (format != null && format.equals("xml")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(deletedEmployee);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(deletedEmployee);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getReason());
    }
}
