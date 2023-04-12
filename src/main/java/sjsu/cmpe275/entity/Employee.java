package sjsu.cmpe275.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

    @EmbeddedId
    private EmployeeId employeeId;
    
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employer_id", nullable = false)
    private String employerId;

    @NotEmpty(message = "Name may not be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Embedded
    private Address address;

    @ManyToOne
    @MapsId("employerId")
    @JoinColumn(name = "employer_id", insertable = false, updatable = false)
    private Employer employer;

    @ManyToOne
    @JoinColumns ({
            @JoinColumn(name = "manager_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "employer_id", referencedColumnName = "employer_id", insertable = false, updatable = false),
    })
    private Employee Manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> reports;

    @ManyToMany
    @JoinTable(
        name = "collaborator",
        joinColumns = {
            @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "employer_id", referencedColumnName = "employer_id", insertable = false, updatable = false)
        },
        inverseJoinColumns = @JoinColumn(name = "collaborator_id")
    )
    private List<Employee> collaborators;

    public Employee() {
    }
}