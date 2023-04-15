package sjsu.cmpe275.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import sjsu.cmpe275.repository.*;
import sjsu.cmpe275.entity.*;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private EntityManager entityManager;

    public Employee createEmployee(ObjectNode body, long employerId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Optional<Employer> optionalEmployer = employerRepository.findById(Long.valueOf(employerId));
        if (!optionalEmployer.isPresent()) {
            return null;
        }

        Employer employer = optionalEmployer.get();
        Employee employee = new Employee();
        String name = body.get("name").asText();
        employee.setName(name);
        String email = body.get("email").asText();
        employee.setEmail(email);
        String title = body.get("title").asText();
        employee.setTitle(title);

        String addressJsonString = objectMapper.writeValueAsString(body.get("address"));
        Address address = objectMapper.readValue(addressJsonString, Address.class);
        employee.setAddress(address);
        employee.setEmployerId(employerId);
        employee.setEmployer(employer);

        Long managerId;
        try {
            managerId = body.get("manager_id").asLong();
        } catch(Exception e) {
            managerId = null;
        }

        if (managerId != null && managerId != 0L) {
            Employee optionalManager = employeeRepository.findByIdAndEmployerId(managerId, employerId);
            employee.setManager(optionalManager);
        }

        // TODO: empty arraylist declarations for reports, collaborators

        Employee savedEmployee = employeeRepository.save(employee);
        entityManager.flush();
        return savedEmployee;
    }

    public Employee getEmployee(EmployeeId employeeId) {
        // TODO: Implement the method to get an employee
        return new Employee();
    }

    public Employee updateEmployee(EmployeeId employeeId, Employee employee) {
        // TODO: Implement the method to update an employee
        return new Employee();
    }

    public void deleteEmployee(EmployeeId employeeId) {
        // TODO: Implement the method to delete an employee
    }
}
