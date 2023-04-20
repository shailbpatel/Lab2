package sjsu.cmpe275.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "employer")
@XmlRootElement(name = "employer")
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank( message = "Name may not be empty or full of white spaces")
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private Address address;

    @Transient
    private List<Employee> employees;

    public Employer(String name, String description, Address address, List<Employee> employees) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.employees = employees;
    }

    public Employer() {
    }

    @XmlElement(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name = "employees")
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @XmlElement(name = "address")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}