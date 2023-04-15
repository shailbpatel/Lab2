package sjsu.cmpe275.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sjsu.cmpe275.entity.Address;
import sjsu.cmpe275.entity.Employee;
import sjsu.cmpe275.entity.Employer;
import sjsu.cmpe275.repository.EmployerRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;


import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Employer createEmployer(ObjectNode body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Employer employer = new Employer();

        String name = body.get("name").asText();
        employer.setName(name);
        String description = body.get("description").asText();
        employer.setDescription(description);

        String addressJsonString = objectMapper.writeValueAsString(body.get("address"));
        Address address = objectMapper.readValue(addressJsonString, Address.class);

        employer.setAddress(address);
        employer.setEmployees(new ArrayList<Employee>());
        Employer savedEmployer = employerRepository.save(employer);
        entityManager.flush();
        return savedEmployer;
    }
    public Employer getEmployer(long id) {
        Optional<Employer> employer = employerRepository.findById(id);
        return employer.orElse(null);
    }

    public List<Employer> getAllEmployers() {
        return null;
        // return employerRepository.findAll();
    }

    public Employer updateEmployer(long id, Employer employerDetails) {
        Optional<Employer> optionalEmployer = employerRepository.findById(id);

        if (optionalEmployer.isPresent()) {
            Employer employer = optionalEmployer.get();
            employer.setName(employerDetails.getName());
            return employerRepository.save(employer);
        }
        return null;
    }

    public void deleteEmployer(long id) {
        employerRepository.deleteById(id);
    }
}
