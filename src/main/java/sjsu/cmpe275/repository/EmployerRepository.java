package sjsu.cmpe275.repository;

import org.springframework.data.repository.CrudRepository;

import sjsu.cmpe275.entity.Employer;

public interface EmployerRepository extends CrudRepository<Employer, Long> {
}