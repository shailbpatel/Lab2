package sjsu.cmpe275.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sjsu.cmpe275.entity.Address;
import sjsu.cmpe275.entity.Employee;
import sjsu.cmpe275.entity.Employer;
import sjsu.cmpe275.repository.EmployerRepository;

import javax.persistence.EntityManager;


import java.util.ArrayList;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Employer createEmployer(String name, String description, String street, String city, String state, String zip) {
        if(employerRepository.findByName(name) != null) {
            return null;
        }
        Employer employer = new Employer();
        employer.setName(name);
        employer.setDescription(description);
        Address address = new Address(street, city, state, zip);
        employer.setAddress(address);
        employer.setEmployees(new ArrayList<Employee>());
        Employer savedEmployer = employerRepository.save(employer);
        entityManager.flush();
        return savedEmployer;
    }
    public Employer getEmployer(long id) {
        Employer employer = employerRepository.findById(id);
        return employer;
    }

    public Iterable<Employer> getAllEmployers() {
//        return null;
         return employerRepository.findAll();
    }

    @Transactional
    public Employer updateEmployer(long employerId, String name, String description, String street, String city, String state, String zip) throws Exception {
        Employer optionalEmployer = employerRepository.findById(employerId);
        if (optionalEmployer == null) {
            throw new Exception("Employer does not exist!");
        }

        if (name != null) {
            optionalEmployer.setName(name);
        }

        if (description != null) {
            optionalEmployer.setDescription(description);
        }

        if (street != null || city != null || state != null || zip != null) {
            Address address = optionalEmployer.getAddress();
            if (address == null) {
                address = new Address();
                optionalEmployer.setAddress(address);
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

        Employer savedEmployer = employerRepository.save(optionalEmployer);
        entityManager.flush();
        return savedEmployer;
    }

    public Employer deleteEmployer(long id) {
        Employer optionalEmployer = employerRepository.findById(id);
        if (optionalEmployer != null) {
            if (optionalEmployer.getEmployees() != null) {
                if(!optionalEmployer.getEmployees().isEmpty())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employer has employees and cannot be deleted.");
            }
            employerRepository.delete(optionalEmployer);
            return optionalEmployer;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found.");
        }
    }
}
