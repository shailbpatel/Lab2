package sjsu.cmpe275.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjsu.cmpe275.entity.Employee;
import sjsu.cmpe275.repository.EmployeeRepository;

@Service
public class CollaboratorService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Boolean addCollaborator(long employerId1, long employeeId1, long employerId2, long employeeId2) {
        if (employerId1 == employerId2 && employeeId1 == employeeId2) {
            throw new IllegalArgumentException("Cannot add an employee as a collaborator of himself/herself.");
        }

        Employee employee1 = employeeRepository.findByIdAndEmployerId(employeeId1, employerId1);
        Employee employee2 = employeeRepository.findByIdAndEmployerId(employeeId2, employerId2);

        if (employee1 == null || employee2 == null ) {
            return false;
        }
        if(employee1.getCollaborators().contains(employee2)) {
            return true;
        }

        employee1.getCollaborators().add(employee2);
        employee2.getCollaborators().add(employee1);

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        return true;
    }

    public Boolean deleteCollaborator(long employerId1, long employeeId1, long employerId2, long employeeId2) throws Exception {
        if (employerId1 == employerId2 && employeeId1 == employeeId2) {
            throw new Exception("Cannot remove an employee as a collaborator of himself/herself.");
        }

        Employee employee1 = employeeRepository.findByIdAndEmployerId(employeeId1, employerId1);
        Employee employee2 = employeeRepository.findByIdAndEmployerId(employeeId2, employerId2);

        if (employee1 == null || employee2 == null ) {
            return false;
        }
        if(!employee1.getCollaborators().contains(employee2)) {
            throw new Exception("Collaboration does not exist.");
        }

        employee1.getCollaborators().remove(employee2);
        employee2.getCollaborators().remove(employee1);

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        return true;
    }
}
