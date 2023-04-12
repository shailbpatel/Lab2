package sjsu.cmpe275.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employer")
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "employer")
    private List<Employee> employees;

    public Employer(String name, String description, Address address, List<Employee> employees) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.employees = employees;
    }

    public Employer() {
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}