package sjsu.cmpe275.entity;

import java.io.Serializable;
import java.util.Objects;

public class EmployeeId implements Serializable {
    private long id;
    private long employerId;

    public EmployeeId() {
    }

    public EmployeeId(long id, long employerId) {
        this.id = id;
        this.employerId = employerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(long employerId) {
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