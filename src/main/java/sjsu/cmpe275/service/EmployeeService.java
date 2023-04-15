package sjsu.cmpe275.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sjsu.cmpe275.repository.*;
import sjsu.cmpe275.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
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

    public long generateEmployeeId(long employerId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Employee> root = cq.from(Employee.class);
        cq.select(cb.max(root.get("id"))).where(cb.equal(root.get("employerId"), employerId));
        Long maxId = entityManager.createQuery(cq).getSingleResult();
        return maxId == null ? 1 : maxId + 1;
    }

    public Employee createEmployee(String name, String email, String title, String street, String city, String state, String zip, Long managerId, long employerId) throws Exception {
        Employer optionalEmployer = employerRepository.findById(employerId);
        if (optionalEmployer == null) {
            throw new Exception("Employer object does not exist!");
        }
        Employee Manager = null;
        if(managerId != null) {
            Manager = employeeRepository.findByIdAndEmployerId(managerId, employerId);
            if(Manager == null) {
              throw new Exception("Manager does not exist!");
            }
        }
        Address address = new Address(street, city, state, zip);
        List<Employee> reports = new ArrayList<>();
        List<Employee> collaborators = new ArrayList<>();
        Employee employee = new Employee(employerId, name, email, title, address, optionalEmployer, Manager, collaborators, reports);

        List<Employee> currentReports = Manager.getReports();
        currentReports.add(employee);
        Manager.setReports(currentReports);

        List<Employee> currentEmpList = optionalEmployer.getEmployees();
        currentEmpList.add(employee);
        optionalEmployer.setEmployees(currentEmpList);

        employee.setId(generateEmployeeId(employerId));
        Employee savedEmployee = employeeRepository.save(employee);
        entityManager.flush();
        return savedEmployee;
    }

    public Employee getEmployee(EmployeeId employeeId) throws Exception {
        Employee optionalEmployee = employeeRepository.findByIdAndEmployerId(employeeId.getId(), employeeId.getEmployerId());
        if (optionalEmployee == null) {
            throw new Exception("Employee does not exist!");
        }
        Employee employee = optionalEmployee;
        Hibernate.initialize(employee.getManager());
        return employee;
    }

    public Employee updateEmployee(EmployeeId employeeId, String name, String email, String title, String street, String city, String state, String zip, Long managerId) throws Exception {
        Employee optionalEmployee = employeeRepository.findByIdAndEmployerId(employeeId.getId(), employeeId.getEmployerId());
        if (optionalEmployee == null) {
            throw new Exception("Employee does not exist!");
        }
        Employee employee = optionalEmployee;

        if (name != null) {
            employee.setName(name);
        }
        if (email != null) {
            employee.setEmail(email);
        }
        if (title != null) {
            employee.setTitle(title);
        }
        if (street != null || city != null || state != null || zip != null) {
            Address address = employee.getAddress();
            if (address == null) {
                address = new Address();
                employee.setAddress(address);
            }
            if (street != null) {
                address.setStreet(street);
            }
            if (city != null) {
                address.setCity(city);
            }
            if (state != null) {
                address.setState(state);
            }
            if (zip != null) {
                address.setZip(zip);
            }
        }
        Long managerEmployerId = employeeId.getEmployerId();
        if (managerId != null && managerEmployerId != null) {
            Employee newManager = employeeRepository.findByIdAndEmployerId(managerId, employeeId.getEmployerId());
            if (newManager == null) {
                throw new Exception("Manager does not exist!");
            }
            if (newManager.equals(employee)) {
                throw new Exception("Employee cannot be their own manager!");
            }
            if (newManager == null && !newManager.getEmployer().equals(employee.getEmployer())) {
                throw new Exception("Manager is not from the same employer!");
            }
            Employee currentManager = employee.getManager();
            List<Employee> oldReports = currentManager.getReports();
            oldReports.add(employee);
            currentManager.setReports(oldReports);

            List<Employee> currentReports = newManager.getReports();
            currentReports.add(employee);
            newManager.setReports(currentReports);
            employee.setManager(newManager);
        } else if (managerId == null && managerEmployerId == null) {
            employee.setManager(null);
        } else {
            throw new Exception("Both manager ID and employer ID must be present, or neither!");
        }

        Employee savedEmployee = employeeRepository.save(employee);
        entityManager.flush();
        return savedEmployee;
    }

    @Transactional
    public Employee deleteEmployee(Long id, Long employerId) throws Exception {
        Employee optionalEmployee = employeeRepository.findByIdAndEmployerId(id, employerId);
        if (optionalEmployee != null) {;
            if (!optionalEmployee.getReports().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee has reports and cannot be deleted.");
            }
            Employee currentManager = optionalEmployee.getManager();
            List<Employee> oldReports = currentManager.getReports();
            oldReports.add(optionalEmployee);

            currentManager.setReports(oldReports);
            employeeRepository.delete(optionalEmployee);
            return optionalEmployee;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found.");
        }
    }
}
