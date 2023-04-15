package sjsu.cmpe275.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "employee")
@XmlRootElement(name = "employee")
@IdClass(EmployeeId.class)
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Id
    @Column(name = "employer_id", nullable = false)
    private long employerId;

    @NotEmpty(message = "Name may not be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Embedded
    private Address address;

    @Transient
    @JoinColumn(name = "employer_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Employer employer;

    private Long manager_id;
    private long manager_employer_id;

    @ManyToOne
    @JoinColumns ({
            @JoinColumn(name = "manager_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "manager_employer_id", referencedColumnName = "employer_id", insertable = false, updatable = false),
    })
    @Access(AccessType.PROPERTY)
    private Employee Manager;

    @ManyToMany
    @JoinTable(
            name = "collaborator",
            joinColumns = {
                    @JoinColumn(name = "employee_id", referencedColumnName = "id"),
                    @JoinColumn(name = "employee_employer_id", referencedColumnName = "employer_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "collaborator_id", referencedColumnName = "id"),
                    @JoinColumn(name = "collaborator_employer_id", referencedColumnName = "employer_id", columnDefinition = "bigint")
            }
    )
    @Access(AccessType.PROPERTY)
    private List<Employee> collaborators;

    @OneToMany(mappedBy = "Manager", cascade = CascadeType.ALL)
    private List<Employee> reports;

    public Employee() {
    }

    public Long getManagerId() {
        if (this.Manager != null) {
            return this.Manager.getId();
        }
        return null;
    }

    public void setManagerId(long managerId) {
        this.manager_id = managerId;
    }

    public long getManagerEmployerId() {
        if (this.Manager != null) {
            return this.Manager.getEmployerId();
        }
        return Long.parseLong(null);
    }

    public void setManagerEmployerId(long managerEmployerId) {
        this.manager_employer_id = managerEmployerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "employer_id")
    public long getEmployerId() {
        return employerId;
    }

    @XmlElement(name = "collaborators")
    public List<Employee> getCollaborators() {
        return collaborators;
    }

    @XmlElement(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCollaborators(List<Employee> collaborators) {
        this.collaborators = collaborators;
    }

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "employer")
    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    @XmlElement(name = "manager")
    public Employee getManager() {
        return Manager;
    }

    public void setManager(Employee manager) {
        this.Manager = manager;
        if (manager != null) {
            this.manager_id = manager.getId();
            this.manager_employer_id = manager.getEmployerId();
        } else {
            this.manager_id = null;
            this.manager_employer_id = Long.parseLong(null);
        }
    }

    @XmlElement(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name = "address")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmployerId(long employerId) {
        this.employerId = employerId;
    }

    @XmlElement(name = "reports")
    public List<Employee> getReports() {
        return reports;
    }

    public void setReports(List<Employee> reports) {
        this.reports = reports;
    }
}