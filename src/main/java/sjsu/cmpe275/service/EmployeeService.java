package sjsu.cmpe275.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjsu.cmpe275.repository.*;
import sjsu.cmpe275.entity.*;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployerRepository employerRepository;

    public Employee createEmployee(long employerId, String name, String email, String title, String street, String city, String state, String zip, long managerId) {
        Optional<Employer> optionalEmployer = employerRepository.findById(employerId);
        if (!optionalEmployer.isPresent()) {
            return null;
        }

        Employer employer = optionalEmployer.get();
        Employee employee = new Employee();
        // employee.setName(name);
        // employee.setEmail(email);
        // employee.setTitle(title);
        Address addr = new Address(street, city, state, zip);
        // employee.setAddress(addr);
        // employee.setEmployer(employer);

        if (managerId != 0L) {
            Optional<Employee> optionalManager = employeeRepository.findById(managerId);
            if (!optionalManager.isPresent()) {
                return null;
            }
            Employee manager = optionalManager.get();
            // employee.setManager(manager);
        }

        return employeeRepository.save(employee);
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
