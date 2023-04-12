package sjsu.cmpe275.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeId implements Serializable {
    private long id;
    private String employerId;

    public EmployeeId() {
    }

    public EmployeeId(long id, String employerId) {
        this.id = id;
        this.employerId = employerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeId that = (EmployeeId) o;
        return id == that.id &&
                Objects.equals(employerId, that.employerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employerId);
    }
}