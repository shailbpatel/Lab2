package sjsu.cmpe275.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjsu.cmpe275.entity.Employer;
import sjsu.cmpe275.repository.EmployerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    public Employer createEmployer(Employer employer) {
        return employerRepository.save(employer);
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
